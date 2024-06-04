function signup() {
    var username = document.getElementById('username').value.trim();
    var phone = document.getElementById('phone').value.trim();
    var password = document.getElementById('password').value.trim();

    // 检查用户名非空
    if (username === '') {
        alert("用户名不能为空");
        return;
    }

    // 正则表达式检查手机号格式
    var phoneRegex = /^1[3456789]\d{9}$/;
    if (!phoneRegex.test(phone)) {
        alert("手机号格式不正确");
        return;
    }

    // 正则表达式检查密码格式
    var passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{8,15}$/;
    if (!passwordRegex.test(password)) {
        alert("密码必须为8-15位字母加数字");
        return;
    }


    // 使用 Ajax 提交表单数据
    $.ajax({
        url: '/JavaWebDemo1_war/registerServlet', // Servlet 映射路径
        type: 'POST', // 使用 POST 方法
        data: {
            username: username,
            phone: phone,
            password: password
        },
        success: function(response) {
            // 处理 Servlet 返回的响应
            alert(response); // 显示响应信息

            // 清空输入信息
            document.getElementById('username').value = '';
            document.getElementById('phone').value = '';
            document.getElementById('password').value = '';

            window.location.href = './exchange.html'; // 跳转到目标页面
        },
        error: function(xhr, status, error) {
            // 处理请求失败的情况
            console.error(error);
            alert("注册失败！");
        }
    });

}