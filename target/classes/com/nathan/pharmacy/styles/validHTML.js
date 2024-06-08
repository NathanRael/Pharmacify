function validHtml(html){
    let splitedValue = html.split(/<|>/).filter(item => item != "");
    for (let i = 0,j = splitedValue.length -1; i < splitedValue.length; i++,j--){
        // console.log(splitedValue[i] , splitedValue[j])
        if (splitedValue[i] !== splitedValue[j].slice(1))
            return false;
    }
    return true;
}

console.log(validHtml("<span></span>")); 