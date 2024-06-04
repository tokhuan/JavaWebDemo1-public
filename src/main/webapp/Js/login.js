// 登陆函数
function login() {
    var phone = document.getElementById('loginPhone').value.trim();
    var password = document.getElementById('loginPassword').value.trim();

    if (phone === '' || password === '') {
        alert("请输入手机号和密码");
        return;
    }

    // 使用 AJAX 发送登录请求
    $.ajax({
        url: '/JavaWebDemo1_war/loginServlet', // Servlet 映射路径
        type: 'POST', // 使用 POST 方法
        data: {
            phone: phone,
            password: password
        },
        success: function(response) {
            // 登录成功，存储用户信息到 localStorage
            localStorage.setItem('userInfo', JSON.stringify(response));
            // 获取存储的用户信息
            let userInfo = JSON.parse(localStorage.getItem('userInfo'));
            console.log(userInfo);
            alert("登录成功!")


            // 进入主页的逻辑
            window.location.href = "exchange.html";
        },
        error: function(xhr, status, error) {
            // 处理请求失败的情况
            console.error(error);
            alert("登录失败！");
        }
    });
}
