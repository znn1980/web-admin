package com.ifacebox.web.admin.logger.service;

import com.ifacebox.web.admin.logger.dao.TraceLoggerDao;
import com.ifacebox.web.admin.logger.model.TraceLogger;
import com.ifacebox.web.admin.logger.model.TraceLoggerQuery;
import com.ifacebox.web.common.model.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author znn
 */
@Service
public class TraceLoggerService {
    private static final Logger logger = LoggerFactory.getLogger(TraceLoggerService.class);
    @Resource
    private TraceLoggerDao traceLoggerDao;

    public List<TraceLogger> query(TraceLoggerQuery traceLoggerQuery) {
        return traceLoggerDao.query(traceLoggerQuery);
    }

    public Long total(TraceLoggerQuery traceLoggerQuery) {
        return traceLoggerDao.total(traceLoggerQuery);
    }

    public void addTraceLogger(UserAgent userAgent, String message, String... params) {
        TraceLogger traceLogger = new TraceLogger();
        if (!ObjectUtils.isEmpty(userAgent.getMasterUser())) {
            traceLogger.setUserId(userAgent.getMasterUser().getUsername());
        }
        traceLogger.setIp(userAgent.getIp());
        traceLogger.setOs(userAgent.getOs());
        traceLogger.setBrowser(userAgent.getBrowser());
        traceLogger.setUserAgent(userAgent.getMessage());
        for (String param : params) {
            message = message.replaceFirst("\\{}", param);
        }
        traceLogger.setMessage(message);
        traceLogger.setCostMillis(System.currentTimeMillis() - userAgent.getDate());
        if (logger.isInfoEnabled()) {
            logger.info("请求【{}】操作【{}】", userAgent.toString(), message);
        }
        traceLoggerDao.save(traceLogger);
    }

    public boolean delete(Integer... id) {
        for (Integer i = 0; id != null && i < id.length; i++) {
            if (!this.delete(id[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean delete(Integer id) {
        return !ObjectUtils.isEmpty(traceLoggerDao.delete(id));
    }

}
