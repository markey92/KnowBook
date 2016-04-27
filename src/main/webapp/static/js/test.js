window.onload=function () {
	console.log("²âÊÔ¿ªÊ¼£¡");
	login("13660208544");//µÇÂ¼²âÊÔ
}
function login (phone) {
	$.post("../login/login",
        {"phone_number":phone, "password":123456},
        function(data) {
            console.log(data);
            }
        )
}