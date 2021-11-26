package com.epam.aop.aspects;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class TaskExecutionUtil
{

    private static Logger logger = LoggerFactory.getLogger(TaskExecutionUtil.class);

    @SafeVarargs
    public static <T> T execute(Task<T> task,
                                int noOfRetryAttempts,
                                long sleepInterval,
                                Class<? extends Throwable>... ignoreExceptions)
    {

//        RetryTemplate retryTemplate = RetryTemplate.builder()
//                .maxAttempts(noOfRetryAttempts)
//                .fixedBackoff(sleepInterval)
//                .build();
//
//        T result= null;
//
//        retryTemplate.execute(result = task.execute());
//        if (noOfRetryAttempts < 1) {
//            noOfRetryAttempts = 1;
//        }
//        Set<Class<? extends Throwable>> ignoreExceptionsSet = new HashSet<>();
//        if (ignoreExceptions != null && ignoreExceptions.length > 0) {
//            for (Class<? extends Throwable> ignoreException : ignoreExceptions) {
//                ignoreExceptionsSet.add(ignoreException);
//            }
//        }
//        logger.debug("noOfRetryAttempts = "+noOfRetryAttempts);
//        logger.debug("ignoreExceptionsSet = "+ignoreExceptionsSet);
//
//        T result = null;
//        for (int retryCount = 1; retryCount <= noOfRetryAttempts; retryCount++) {
//            logger.debug("Executing the task. Attemp#"+retryCount);
//            try {
//                result = task.execute();
//                break;
//            } catch (RuntimeException t) {
//                Throwable e = t.getCause();
//                logger.error(" Caught Exception class"+e.getClass());
//                for (Class<? extends Throwable> ignoreExceptionClazz : ignoreExceptionsSet) {
//                    logger.error(" Comparing with Ignorable Exception : "+ignoreExceptionClazz.getName());
//
//                    if (!ignoreExceptionClazz.isAssignableFrom(e.getClass())) {
//                        logger.error("Encountered exception which is not ignorable: "+e.getClass());
////                        logger.error("Throwing exception to the caller");
//                        throw t;
//                    }
//                }
//                logger.error("Failed at Retry attempt :" + retryCount + " of : " + noOfRetryAttempts);
//                if (retryCount >= noOfRetryAttempts) {
//                    logger.error("Maximum retrial attempts exceeded.");
////                    logger.error("Throwing exception to the caller");
//                    throw t;
//                }
//                try {
//                    Thread.sleep(sleepInterval);
//                } catch (InterruptedException e1) {
//                    //Intentionally left blank
//                }
//            }
//        }
//        return result;
        return  null;
    }

}