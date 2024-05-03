package com.example.community.controller;

import cn.hutool.core.date.DateUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.easysdk.factory.Factory;
import com.example.community.config.AliPayConfig;
import com.example.community.dto.FormData;
import com.example.community.dto.Orders;
import com.example.community.service.OrdersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/alipay")
public class AliPayController {

    private static final String GATEWAY_URL="https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    private static final String FORMAT="JSON";

    private static final String CHARSET="UTF-8";

    private static final String SING_TYPE="RSA2";
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private AliPayConfig aliPayConfig;

    @GetMapping("/pay") //
    public void pay(Long orderId, HttpServletResponse httpResponse) throws Exception{
        Orders orders = ordersService.findByOrderId(orderId);
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAlipayPublicKey(), SING_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        request.setBizContent("{\"out_trade_no\":\"" + orders.getOrderId() + "\","
                + "\"total_amount\":\"" + orders.getPrice() + "\","
                + "\"subject\":\"" +orders.getGoodsName() + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            // 调用SDK生成表单
            form = alipayClient.pageExecute(request).getBody();
            log.info("Alipay response: {}", form);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();

// 将表单信息构建为 FormData 对象
        FormData formData = new FormData();
        formData.setFormContent(form);

// 将 FormData 对象转换为 JSON 格式的字符串
        String jsonFormData = objectMapper.writeValueAsString(formData);

// 设置响应的 Content-Type 为 application/json
        httpResponse.setContentType("application/json");

// 将 JSON 格式的表单信息写入响应
        httpResponse.getWriter().write(jsonFormData);
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }
    @PostMapping ("/notify")  // 注意这里必须是POST接口
    public void payNotify(HttpServletRequest request) throws Exception {
        if(request.getParameter("trade_status").equals("TRADE_SUCCESS")){
            System.out.println("==============支付异步回调===================");
            Map<String,String> params = new HashMap<>();
            Map<String,String[]> requestParam = request.getParameterMap();
            for(String name : requestParam.keySet()){
                params.put(name,request.getParameter(name));
            }
            // 支付宝验签
            if (Factory.Payment.Common().verifyNotify(params)) {
                // 验签通过
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));

                String orderIdStr = params.get("out_trade_no");
                Long orderId = Long.parseLong(orderIdStr);
                Orders orders = ordersService.findByOrderId(orderId);
                orders.setStatus(0);
                // 更新订单未已支付
                ordersService.updateById(orders);
            }
        }
    }
}

