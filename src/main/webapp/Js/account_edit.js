// 从 localStorage 获取用户信息 (实际应用中应从后端获取)
const userInfo = JSON.parse(localStorage.getItem('userInfo'));

// 从 localStorage 获取 phone
const phone = userInfo.phone;

//使用phone以获取完整信息
$.ajax({
    url:'/JavaWebDemo1_war/EditPart',
    type: 'GET',
    data: { phone: phone },
    success: function(response) {
        if (response.success) {
            displayUserInfo(response.UserInfo);
        } else{
            alert('获取用户信息失败：' + response.message);
        }
    },
    error:function (){
        alert('发生错误，请稍后重试……');
    }
});

function displayUserInfo(UserInfo){

}
