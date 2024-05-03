
function isValidUsername (str) {
  return ['admin', 'editor'].indexOf(str.trim()) >= 0;
}

function isExternal (path) {
  return /^(https?:|mailto:|tel:)/.test(path);
}

function isCellPhone (val) {
  if (!/^1(3|4|5|6|7|8)\d{9}$/.test(val)) {
    return false
  } else {
    return true
  }
}


//校验姓名
function checkName (rule, value, callback){
  if (value == "") {
    callback(new Error("请输入姓名"))
  } else if (value.length > 10) {
    callback(new Error("姓名长度应是1-10"))
  } else {
    callback()
  }
}

function checkPhone (rule, value, callback){
  if (value == "") {
    callback(new Error("请输入手机号"))
  } else if (!isCellPhone(value)) {//引入methods中封装的检查手机格式的方法
    callback(new Error("请输入正确的手机号!"))
  } else {
    callback()
  }
}


function validID (rule,value,callback) {
  // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
  const reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
  if(value == '') {
    callback(new Error('请输入身份证号码'))
  } else if (reg.test(value)) {
    callback()
  } else {
    callback(new Error('身份证号码不正确'))
  }
}
function checkPassword (rule,value,callback)
{
  if (value == "") {
    callback(new Error("请输入密码"))
  } else if (value.length > 12) {
    callback(new Error("密码长度应是6-12"))
  }else if(value.length<6) {
    callback(new Error("密码长度应是6-12"))
  }else {
    callback()
  }
}

function checkConfirmPassword (rule,value,callback,password)
{
  if (value == "") {
    callback(new Error("请输入确认密码"))
  } else if (value.length > 12) {
    callback(new Error("密码长度应是6-12"))
  }else if(value.length<6) {
    callback(new Error("密码长度应是6-12"))
  }else if(value !== password){
    callback(new Error("两次密码输入不一致"))
  } else {
    callback()
  }
}


function checkHousePrice(rule,value,callback)
{
  if (value=="") {
    callback(new Error('请填写房屋价格'))
  } else {
    const reg = /^\d+(\.\d{0,2})?$/
    if (reg.test(value)) {
      if(value < 200000){
        callback()
      }else{
        callback(new Error('房屋价格小于200000'))
      }
    } else {
      callback(new Error('房屋价格格式只能为数字,且最多保留两位小数'))
    }
  }
}

function checkParkingPrice(rule,value,callback)
{
  if (value=="") {
    callback(new Error('请填写车位价格'))
  } else {
    const reg = /^\d+(\.\d{0,2})?$/
    if (reg.test(value)) {
      if(value < 200000){
        callback()
      }else{
        callback(new Error('车位价格小于200000'))
      }
    } else {
      callback(new Error('车位价格格式只能为数字,且最多保留两位小数'))
    }
  }
}

function checkCategoryName(rule,value,callback)
{
  if(value==""){
    callback(new Error('请输入小区名'))
  }
  else{
    callback()
  }
}

function checkCategoryId(rule,value,callback,idn,categoryList)
{
  const isLong = /^-?\d{1,19}$/.test(value); // 使用正则表达式检查是否为长整型
  if (!isLong) {
    callback(new Error('请输入有效的数字ID'));
  } else {
    // 在这里可以使用 categoryList，如果有的话
    console.log(categoryList);
    // 检查是否与 categoryList 中的某个 id 相匹配
    const isIdRepeated = categoryList.some(item => item.idn === idn);
    if (isIdRepeated) {
      callback(new Error('ID重复'));
    } else {
      // 其他验证逻辑...
      callback(); // 通过验证，不触发错误
    }
  }

}

/*
const checkCategoryIdD=(id)=>{
  return $axios({
    url: `/category/repeatId?id=${id}`,
    method: 'get',
  })
}*/
