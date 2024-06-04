document.addEventListener('DOMContentLoaded', function () {
    const profileImageInput = document.getElementById('profile-image');
    const profileImagePreview = document.getElementById('profile-image-preview');

    // 头像选择事件监听器
    profileImageInput.addEventListener('change', () => {
        const file = profileImageInput.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = (e) => {
                profileImagePreview.src = e.target.result; // 立即更新图片预览
            };
            reader.readAsDataURL(file);
        }
    });

    // 从 localStorage 获取用户信息
    const userInfo = JSON.parse(localStorage.getItem('userInfo'));

    // 从 localStorage 获取 phone
    const phone = userInfo.phone;

    // 使用 phone 从后端获取完整的用户信息（包括 email、headphoto、gender）
    $.ajax({
        url: '/your-servlet-url', // 替换为你的 Servlet URL
        type: 'GET',
        data: { phone: phone },
        success: function(response) {
            if (response.success) {
                // 将获取到的完整用户信息显示在页面上
                displayUserInfo(response.userInfo);
            } else {
                alert('获取用户信息失败：' + response.message);
            }
        },
        error: function() {
            alert('发生错误，请稍后重试。');
        }
    });

    function displayUserInfo(userInfo) {
        // 邮箱
        document.getElementById('email').value = userInfo.email || '';

        // 密码 (始终为空，不显示原始密码)
        document.getElementById('password').value = '';

        // 性别
        const genderSelect = document.getElementById('gender');
        for (const option of genderSelect.options) {
            if (option.value === userInfo.gender) {
                option.selected = true;
                break;
            }
        }

        // 用户名
        document.getElementById('username').value = userInfo.username || '';

        // 如果用户有头像，则显示用户头像，否则显示默认头像
        if (userInfo.headphoto) {
            profileImagePreview.src = `data:image/jpeg;base64,${userInfo.headphoto}`;
        }
    }

    // 表单提交事件
    const editForm = document.getElementById('edit-form');
    editForm.addEventListener('submit', (event) => {
        event.preventDefault(); // 阻止表单默认提交行为

        const formData = new FormData(editForm); // 获取表单数据
        const profileImage = document.getElementById('profile-image');
        if (profileImage.files.length > 0) {
            formData.append('avatar', profileImage.files[0]); // 将头像文件添加到表单数据
        }

        // 将表单数据（包括头像）提交到服务器
        $.ajax({
            url: '/your-servlet-url', // 替换为你的 Servlet URL
            type: 'POST',
            data: formData,
            processData: false, // 告诉 jQuery 不要处理数据
            contentType: false, // 告诉 jQuery 不要设置内容类型
            success: function(response) {
                if (response.success) {
                    alert('修改成功！');

                    // 更新 localStorage 中的用户信息
                    const updatedUserInfo = {
                        ...userInfo,
                        email: response.email || userInfo.email,
                        gender: response.gender || userInfo.gender,
                        username: response.username || userInfo.username,
                        headphoto: response.headphoto || userInfo.headphoto
                    };

                    localStorage.setItem('userInfo', JSON.stringify(updatedUserInfo));

                    // 可选：刷新页面或跳转到其他页面
                    // location.reload();
                } else {
                    alert('修改失败：' + response.message);
                }
            },
            error: function() {
                alert('发生错误，请稍后重试。');
            }
        });
    });

    // 邮箱验证函数
    function validateEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    }
});
