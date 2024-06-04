let userInfo = JSON.parse(localStorage.getItem('userInfo'));

// 更新下拉菜单中的用户信息
if (userInfo) {
    document.querySelector('.name').textContent = `${userInfo.username}，您好！`;
    document.querySelector('.phone').textContent = userInfo.phone;
} else {
    // 未登录状态
    document.querySelector('.name').textContent = "你好，请先登录";
    document.querySelector('.phone').textContent = ""; // 清空邮箱信息
}