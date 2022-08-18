function validateInteger(_1){
var _2=true;
var _3=null;
var i=0;
var _5=new Array();
var _6=eval("new "+jcv_retrieveFormName(_1)+"_IntegerValidations()");
for(var x in _6){
if(!jcv_verifyArrayElement(x,_6[x])){
continue;
}
var _8=_1[_6[x][0]];
if(!jcv_isFieldPresent(_8)){
continue;
}
if((_8.type=="hidden"||_8.type=="text"||_8.type=="textarea"||_8.type=="select-one"||_8.type=="radio")){
var _9="";
if(_8.type=="select-one"){
var si=_8.selectedIndex;
if(si>=0){
_9=_8.options[si].value;
}
}else{
_9=_8.value;
}
if(_9.length>0){
if(!jcv_isDecimalDigits(_9)){
_2=false;
if(i==0){
_3=_8;
}
_5[i++]=_6[x][1];
}else{
var _b=parseInt(_9,10);
if(isNaN(_b)||!(_b>=-2147483648&&_b<=2147483647)){
if(i==0){
_3=_8;
}
_5[i++]=_6[x][1];
_2=false;
}
}
}
}
}
if(_5.length>0){
jcv_handleErrors(_5,_3);
}
return _2;
}

