package com.aeye.aeaimb.common.mybatis.handler;

import com.aeye.aeaimb.common.core.util.R;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLSyntaxErrorException;

/**
 * Mybatis异常处理器
 *
 * @author Lion Li
 */
@Slf4j
@RestControllerAdvice
@Order(1000)
public class MybatisExceptionHandler {

    /**
     * sql 异常, 避免 sql 响应到前端
     */
    @ExceptionHandler(SQLSyntaxErrorException.class)
    public R<Void> handler(SQLSyntaxErrorException ex, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.error("请求路径[{}] sql 异常, 检查 sql ",request.getRequestURI(), ex);
        return R.failed().msg("数据库系统异常, 请联系管理员");
    }

    /**
     * 主键或UNIQUE索引，数据重复异常
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public R<Void> handleDuplicateKeyException(DuplicateKeyException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',数据库中已存在记录'{}'", requestURI, e.getMessage());
        return R.failed().msg("数据库中已存在该记录，请联系管理员确认");
    }

    /**
     * Mybatis系统异常 通用处理
     */
    @ExceptionHandler(MyBatisSystemException.class)
    public R<Void> handleCannotFindDataSourceException(MyBatisSystemException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String message = e.getMessage();
        if ("CannotFindDataSourceException".contains(message)) {
            log.error("请求地址'{}', 未找到数据源", requestURI);
            return R.failed().msg("未找到数据源，请联系管理员确认");
        }
        log.error("请求地址'{}', Mybatis系统异常", requestURI, e);
        return R.failed().msg(message);
    }

}
