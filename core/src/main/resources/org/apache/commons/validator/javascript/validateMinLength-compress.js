function validateMinLength(_1){
var _2=true;
var _3=null;
var i=0;
var _5=new Array();
var _6=eval("new "+jcv_retrieveFormName(_1)+"_minlength()");
for(var x in _6){
if(!jcv_verifyArrayElement(x,_6[x])){
continue;
}
var _8=_1[_6[x][0]];
if(!jcv_isFieldPresent(_8)){
continue;
}
if((_8.type=="hidden"||_8.type=="text"||_8.type=="password"||_8.type=="textarea")){
var _9=_6[x][2]("lineEndLength");
var _a=0;
if(_9){
var _b=0;
var _c=0;
var _d=0;
while(_d<_8.value.length){
var _e=_8.value.charAt(_d);
if(_e=="\r"){
_b++;
}
if(_e=="\n"){
_c++;
}
_d++;
}
var _f=parseInt(_9);
_a=(_c*_f)-(_b+_c);
}
var _10=parseInt(_6[x][2]("minlength"));
if((trim(_8.value).length>0)&&((_8.value.length+_a)<_10)){
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

