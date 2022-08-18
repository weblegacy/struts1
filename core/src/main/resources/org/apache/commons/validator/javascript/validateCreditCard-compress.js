function validateCreditCard(_1){
var _2=true;
var _3=null;
var i=0;
var _5=new Array();
var _6=eval("new "+jcv_retrieveFormName(_1)+"_creditCard()");
for(var x in _6){
if(!jcv_verifyArrayElement(x,_6[x])){
continue;
}
var _8=_1[_6[x][0]];
if(!jcv_isFieldPresent(_8)){
continue;
}
if((_8.type=="text"||_8.type=="textarea")&&(_8.value.length>0)){
if(!jcv_luhnCheck(_8.value)){
if(i==0){
_3=_8;
}
_5[i++]=_6[x][1];
_2=false;
}
}
}
if(_5.length>0){
jcv_handleErrors(_5,_3);
}
return _2;
}
function jcv_luhnCheck(_9){
if(jcv_isLuhnNum(_9)){
var _a=_9.length;
var _b=_a&1;
var _c=0;
for(var _d=0;_d<_a;_d++){
var _e=parseInt(_9.charAt(_d));
if(!((_d&1)^_b)){
_e*=2;
if(_e>9){
_e-=9;
}
}
_c+=_e;
}
if(_c==0){
return false;
}
if(_c%10==0){
return true;
}
}
return false;
}
function jcv_isLuhnNum(_f){
_f=_f.toString();
if(_f.length==0){
return false;
}
for(var n=0;n<_f.length;n++){
if((_f.substring(n,n+1)<"0")||(_f.substring(n,n+1)>"9")){
return false;
}
}
return true;
}

