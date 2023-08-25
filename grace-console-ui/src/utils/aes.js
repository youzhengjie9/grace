let CryptoJS = require('crypto-js');

// AES加密
export function aesEncrypt(data, key) {
    var sKey = CryptoJS.enc.Utf8.parse(key);
    var sContent = CryptoJS.enc.Utf8.parse(data);
    var encrypted = CryptoJS.AES.encrypt(sContent, sKey, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    })
    return encrypted.toString();
}

// AES解密
export function aesDecrypt(data, key) {
    var sKey = CryptoJS.enc.Utf8.parse(key);
    var decrypt = CryptoJS.AES.decrypt(data, sKey, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    })
    return CryptoJS.enc.Utf8.stringify(decrypt).toString();
}