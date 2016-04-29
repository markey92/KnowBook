window.onload=function () {
	console.log("测试开始");
	login("13660208544");
//	createSell();
	getBuyBook();
	getWantBook();
	myPicture();
//	createWish();
	fragmentBuy();
	fragmentBuySome();
	fragmentWantSome();
	detailWant(2);
	detailBuy(2);
}
function detailBuy(id){
	$.post("../sellerMarket/detailBuy",
			{"BuyBookId":id},
			function(data){
		console.log(data);
	})
}
function fragmentBuySome(){
	$.post("../sellerMarket/fragmentBuySome",
			{"Type":"",
		     "sellType":"",
		     "pageNow":"2",
		     "pageSize":"1"},
			function(data){
		console.log(data);
	})
}
function fragmentBuy(){
	$.post("../sellerMarket/fragmentBuy",
			{
		     "pageNow":"1",
	         "pageSize":"2"
			},
			function(data){
		console.log(data);
	})
}
function createSell () {
	$.post("../sellerMarket/createBuy",
        {"BuyBookName":"杉杉来吃",
		 "BuyBookPicture":"e:/hellwooerlj.jpg",
		 "BuyBookAuthor":"顾漫",
		 "Type":"言情小说",
		 "BuyBookDescript":"这是一本很好看的小说哦",
		 "SellType":"零售",
		 "price":"9.98",
		 "newOrold":"旧"
        },
        function(data) {
            console.log(data);
            }
        )
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
			{"type":"小说",
		     "pageNow":"1",
		     "pageSize":"1"},
			function(data){
		console.log(data);
	})
}
function fragmentWant(){
	$.post("../wish/fragmentWant",
			{
		     "pageNow":"1",
	         "pageSize":"1"
			},
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