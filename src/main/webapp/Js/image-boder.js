const img = document.getElementById('profileImage');

img.onload = function() {
    const vibrant = new Vibrant(img);
    const swatches = vibrant.swatches();

    // 尝试获取 Vibrant 色板，如果不存在则获取其他色板，最后使用默认颜色
    let borderColor = swatches['Vibrant']?.getHex() ||
        swatches['DarkVibrant']?.getHex() ||
        swatches['LightVibrant']?.getHex() ||
        swatches['Muted']?.getHex() ||
        swatches['DarkMuted']?.getHex() ||
        swatches['LightMuted']?.getHex() ||
        '#fff'; // 默认白色

    img.style.boxShadow = `0 0 0 3px ${borderColor}`;
};