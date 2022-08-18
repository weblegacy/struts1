function validateEmail(_1){
var _2=true;
var _3=null;
var i=0;
var _5=new Array();
var _6=eval("new "+jcv_retrieveFormName(_1)+"_email()");
for(var x in _6){
if(!jcv_verifyArrayElement(x,_6[x])){
continue;
}
var _8=_1[_6[x][0]];
if(!jcv_isFieldPresent(_8)){
continue;
}
if((_8.type=="hidden"||_8.type=="text"||_8.type=="textarea")&&(_8.value.length>0)){
if(!jcv_checkEmail(_8.value)){
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
function jcv_checkEmail(_9){
if(_9.length==0){
return true;
}
var _a=0;
var _b=/^(com|net|org|edu|int|mil|gov|arpa|biz|aero|name|coop|info|pro|museum)$/;
var _c=/^(.+)@(.+)$/;
var _d="\\(\\)><@,;:\\\\\\\"\\.\\[\\]";
var _e="[^\\s"+_d+"]";
var _f="(\"[^\"]*\")";
var _10=/^\[(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})\]$/;
var _11=_e+"+";
var _12="("+_11+"|"+_f+")";
var _13=new RegExp("^"+_12+"(\\."+_12+")*$");
var _14=new RegExp("^"+_11+"(\\."+_11+")*$");
var _15=_9.match(_c);
if(_15==null){
return false;
}
var _16=_15[1];
var _17=_15[2];
for(i=0;i<_16.length;i++){
if(_16.charCodeAt(i)>127){
return false;
}
}
for(i=0;i<_17.length;i++){
if(_17.charCodeAt(i)>127){
return false;
}
}
if(_16.match(_13)==null){
return false;
}
var _19=_17.match(_10);
if(_19!=null){
for(var i=1;i<=4;i++){
if(_19[i]>255){
return false;
}
}
return true;
}
var _1a=new RegExp("^"+_11+"$");
var _1b=_17.split(".");
var len=_1b.length;
for(i=0;i<len;i++){
if(_1b[i].search(_1a)==-1){
return false;
}
}
if(_a&&_1b[_1b.length-1].length!=2&&_1b[_1b.length-1].search(_b)==-1){
return false;
}
if(len<2){
return false;
}
return true;
}

