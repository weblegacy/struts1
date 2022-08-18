function validateFloatRange(_1){
var _2=true;
var _3=null;
var i=0;
var _5=new Array();
var _6=eval("new "+jcv_retrieveFormName(_1)+"_floatRange()");
for(var x in _6){
if(!jcv_verifyArrayElement(x,_6[x])){
continue;
}
var _8=_1[_6[x][0]];
if(!jcv_isFieldPresent(_8)){
continue;
}
if((_8.type=="hidden"||_8.type=="text"||_8.type=="textarea")&&(_8.value.length>0)){
var _9=parseFloat(_6[x][2]("min"));
var _a=parseFloat(_6[x][2]("max"));
var _b=parseFloat(_8.value);
if(!(_b>=_9&&_b<=_a)){
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

