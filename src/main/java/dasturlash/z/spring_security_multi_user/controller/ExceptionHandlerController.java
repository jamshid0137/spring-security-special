package dasturlash.z.spring_security_multi_user.controller;

import dasturlash.z.spring_security_multi_user.exception.AppBadRequestException;
import dasturlash.z.spring_security_multi_user.exception.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//package dasturlash.z.spring_security_multi_user.controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler({ItemNotFoundException.class, AppBadRequestException.class})
    public ResponseEntity<String> handler(RuntimeException e){//runtime exceptionni ushlaysan degani
        //e.printStackTrace();//hatoni consolga chiqarib qo'yish.qizarib chiqib durishi
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());//xato malumot unaqa malumot yo'q
    }

    @ExceptionHandler({AuthorizationDeniedException.class})
    public ResponseEntity<String> handlerRuntime(AuthorizationDeniedException e){//runtime exceptionni ushlaysan degani
        //e.printStackTrace();//hatoni consolga chiqarib qo'yish.
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handlerRuntime(RuntimeException e){//runtime exceptionni ushlaysan degani
        e.printStackTrace();//hatoni consolga chiqarib qo'yish.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
/*
ItemNotFoundException faqat throw qilish uchun kerak, lekin javobni qayta ishlash uchun @ControllerAdvice kerak.
Agar @ControllerAdvice bo‘lmasa, Spring standart xato sahifasini yoki noto‘g‘ri HTTP status kodini qaytarishi mumkin.


*/
//Bizda controllergacha excrp kelsa va controllerni sindirsa try catch yozmoqchimiz
//Misol uchun getall hammaga ochiq lekin biz idni olmoqchi bo'lsak 401 qaytarvotti.Bu 500 qaytarishi kk edi.
//Controllerni o'zigacha kelib xato bersagina ishlaydi bu  class aks xolda ishlamidi.
//controllerga kirmasdan hato sodir bo'lsa controlleradvice ishlamaydi.
// */