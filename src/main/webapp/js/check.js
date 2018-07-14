function checknamenull(name) {
	name = name.trim();
	if(name == ""){
		return false;
	}
	return true;
}

function checknamelength(name) {
	if(name.length<6 || name.length>16){
		return  false;
	}
	return true;
}

function transCategory(category){
	var s;
	if(category =="novel"){
		s="小说";
	}else if(category=="cartoon"){
		s="童书";
	}else if(category=="study"){
		s="学习考试";
	}else if(category=="literature"){
		s="文学";
	}else if(category=="music"){
		s="音乐";
	}else if(category=="art"){
		s="艺术";
	}else if(category=="science"){
		s="科学";
	}
	return s;
}
	
