function validateFloat(_1){
var _2=true;
var _3=null;
var i=0;
var _5=new Array();
var _6=eval("new "+jcv_retrieveFormName(_1)+"_FloatValidations()");
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
var _b=_9.split(".");
var _c=0;
var _d=_b.join("");
while(_d.charAt(_c)=="0"){
_c++;
}
var _e=_d.substring(_c,_d.length);
if(!jcv_isAllDigits(_e)||_b.length>2){
_2=false;
if(i==0){
_3=_8;
}
_5[i++]=_6[x][1];
}else{
var _f=parseFloat(_9);
if(isNaN(_f)){
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

