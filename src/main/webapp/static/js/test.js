window.onload=function () {
	console.log("测试开始");
	login("13660208544");
//	getBuyBook();
//	getWantBook();
//	myPicture();
//	createWish();
//	fragmentWant();
//	fragmentWantSome();
	detailWant(2);
}
function detailWant(id){
	$.post("../wish/detailWant",
			{"WantBookId":id},
			function(data){
		console.log(data);
	})
}
function fragmentWantSome(){
	$.post("../wish/fragmentWantSome",
			{"type":"小说"},
			function(data){
		console.log(data);
	})
}
function fragmentWant(){
	$.get("../wish/fragmentWant",
			function(data){
		console.log(data);
	})
}
function createWish () {
	$.post("../wish/createWant",
        {"WantBookName":"哈利波特",
		 "WantBookPicture":"e:/helibote.jpg",
		 "WantBookAuthor":"J~K luolin",
		 "WantBookContent":"我想要看哈利波特全集",
		 "WantBookPay":"我唱歌给你听呀！",
		 "WantBookType":"小说",
		 "WishPostiton":"{100.245,225.2547}"},
        function(data) {
            console.log(data);
            }
        )
}
function myPicture(){
	$.get("../login/myPicture",
			function(data){
				console.log(data);
			}
	)
}
function login (phone) {
	$.post("../users/login",
        {"phoneNumber":phone, "password":123456},
        function(data) {
            console.log(data);
            }
        )
}
function getBuyBook(){
	$.get("../sellerMarket/myBuyBook",
			function(data){
				console.log(data);
			}
	)
}
function getWantBook(){
	$.get("../wish/myWish",
			function(data){
				console.log(data);
			}
	)
}