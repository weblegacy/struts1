function validateDate(_1){
var _2=true;
var _3=null;
var i=0;
var _5=new Array();
var _6=eval("new "+jcv_retrieveFormName(_1)+"_DateValidations()");
for(var x in _6){
if(!jcv_verifyArrayElement(x,_6[x])){
continue;
}
var _8=_1[_6[x][0]];
if(!jcv_isFieldPresent(_8)){
continue;
}
var _9=_8.value;
var _a=true;
var _b=_6[x][2]("datePatternStrict");
if(_b==null){
_b=_6[x][2]("datePattern");
_a=false;
}
if((_8.type=="hidden"||_8.type=="text"||_8.type=="textarea")&&(_9.length>0)&&(_b.length>0)){
var _c="MM";
var _d="dd";
var _e="yyyy";
var _f=_b.indexOf(_c);
var _10=_b.indexOf(_d);
var _11=_b.indexOf(_e);
if((_10<_11&&_10>_f)){
var _12=_f+_c.length;
var _13=_10+_d.length;
var _14=_b.substring(_12,_12+1);
var _15=_b.substring(_13,_13+1);
if(_12==_10&&_13==_11){
dateRegexp=_a?new RegExp("^(\\d{2})(\\d{2})(\\d{4})$"):new RegExp("^(\\d{1,2})(\\d{1,2})(\\d{4})$");
}else{
if(_12==_10){
dateRegexp=_a?new RegExp("^(\\d{2})(\\d{2})["+_15+"](\\d{4})$"):new RegExp("^(\\d{1,2})(\\d{1,2})["+_15+"](\\d{4})$");
}else{
if(_13==_11){
dateRegexp=_a?new RegExp("^(\\d{2})["+_14+"](\\d{2})(\\d{4})$"):new RegExp("^(\\d{1,2})["+_14+"](\\d{1,2})(\\d{4})$");
}else{
dateRegexp=_a?new RegExp("^(\\d{2})["+_14+"](\\d{2})["+_15+"](\\d{4})$"):new RegExp("^(\\d{1,2})["+_14+"](\\d{1,2})["+_15+"](\\d{4})$");
}
}
}
var _16=dateRegexp.exec(_9);
if(_16!=null){
if(!jcv_isValidDate(_16[2],_16[1],_16[3])){
if(i==0){
_3=_8;
}
_5[i++]=_6[x][1];
_2=false;
}
}else{
if(i==0){
_3=_8;
}
_5[i++]=_6[x][1];
_2=false;
}
}else{
if((_f<_11&&_f>_10)){
var _12=_10+_d.length;
var _13=_f+_c.length;
var _14=_b.substring(_12,_12+1);
var _15=_b.substring(_13,_13+1);
if(_12==_f&&_13==_11){
dateRegexp=_a?new RegExp("^(\\d{2})(\\d{2})(\\d{4})$"):new RegExp("^(\\d{1,2})(\\d{1,2})(\\d{4})$");
}else{
if(_12==_f){
dateRegexp=_a?new RegExp("^(\\d{2})(\\d{2})["+_15+"](\\d{4})$"):new RegExp("^(\\d{1,2})(\\d{1,2})["+_15+"](\\d{4})$");
}else{
if(_13==_11){
dateRegexp=_a?new RegExp("^(\\d{2})["+_14+"](\\d{2})(\\d{4})$"):new RegExp("^(\\d{1,2})["+_14+"](\\d{1,2})(\\d{4})$");
}else{
dateRegexp=_a?new RegExp("^(\\d{2})["+_14+"](\\d{2})["+_15+"](\\d{4})$"):new RegExp("^(\\d{1,2})["+_14+"](\\d{1,2})["+_15+"](\\d{4})$");
}
}
}
var _16=dateRegexp.exec(_9);
if(_16!=null){
if(!jcv_isValidDate(_16[1],_16[2],_16[3])){
if(i==0){
_3=_8;
}
_5[i++]=_6[x][1];
_2=false;
}
}else{
if(i==0){
_3=_8;
}
_5[i++]=_6[x][1];
_2=false;
}
}else{
if((_f>_11&&_f<_10)){
var _12=_11+_e.length;
var _13=_f+_c.length;
var _14=_b.substring(_12,_12+1);
var _15=_b.substring(_13,_13+1);
if(_12==_f&&_13==_10){
dateRegexp=_a?new RegExp("^(\\d{4})(\\d{2})(\\d{2})$"):new RegExp("^(\\d{4})(\\d{1,2})(\\d{1,2})$");
}else{
if(_12==_f){
dateRegexp=_a?new RegExp("^(\\d{4})(\\d{2})["+_15+"](\\d{2})$"):new RegExp("^(\\d{4})(\\d{1,2})["+_15+"](\\d{1,2})$");
}else{
if(_13==_10){
dateRegexp=_a?new RegExp("^(\\d{4})["+_14+"](\\d{2})(\\d{2})$"):new RegExp("^(\\d{4})["+_14+"](\\d{1,2})(\\d{1,2})$");
}else{
dateRegexp=_a?new RegExp("^(\\d{4})["+_14+"](\\d{2})["+_15+"](\\d{2})$"):new RegExp("^(\\d{4})["+_14+"](\\d{1,2})["+_15+"](\\d{1,2})$");
}
}
}
var _16=dateRegexp.exec(_9);
if(_16!=null){
if(!jcv_isValidDate(_16[3],_16[2],_16[1])){
if(i==0){
_3=_8;
}
_5[i++]=_6[x][1];
_2=false;
}
}else{
if(i==0){
_3=_8;
}
_5[i++]=_6[x][1];
_2=false;
}
}else{
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
function jcv_isValidDate(day,_18,_19){
if(_18<1||_18>12){
return false;
}
if(day<1||day>31){
return false;
}
if((_18==4||_18==6||_18==9||_18==11)&&(day==31)){
return false;
}
if(_18==2){
var _1a=(_19%4==0&&(_19%100!=0||_19%400==0));
if(day>29||(day==29&&!_1a)){
return false;
}
}
return true;
}

