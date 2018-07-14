function addCart(id){
			//alert(id);
			$.ajax({
				url:"addCart",
				data:{"id":id},
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.msg=="true"){
						alert("加入购物车成功");
					}else if(data.msg=="full"){
						alert("库存不足");
					}
				}
					
			});
			
			
		}