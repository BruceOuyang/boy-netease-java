package club.bugmakers.boyneteasejava.threadpool;

import lombok.extern.slf4j.Slf4j;

/**
 * ThreadStateChangeDemo
 *
 * @Author Bruce
 * @Date 2018/12/23 23:27
 * @Version 1.0
 **/
@Slf4j
public class ThreadStateChangeDemo {

    public static void main(String [] args) throws InterruptedException {

        threadStateChangeTest1();
        threadStateChangeTest2();
        threadStateChangeTest3();
    }

    public static void threadStateChangeTest1() throws InterruptedException {

        // 第一种状态切换：新建 -> 运行 -> 终止
        log.info("##########第一种状态切换：新建 -> 运行 -> 终止##########");
        Thread thread1 = new Thread(() -> {
            log.info("thread1 当前状态：{}", Thread.currentThread().getState().toString());
            log.info("thread1 执行了");
        });
        log.info("没有调用 start 方法， thread1 当前状态：{}", thread1.getState().toString());
        thread1.start();
        Thread.sleep(2000L);
        log.info("调用了 start 方法 2 秒后， thread1 当前状态：{}", thread1.getState().toString());

        // 注意：线程终止后，再调用 start 会抛出 IllegalThreadStateException 异常
//        thread1.start();
        log.info("");
    }

    public static void threadStateChangeTest2() throws InterruptedException {
        // 第二种状态切换：新建 -> 运行 -> 等待 -> 运行 -> 终止
        log.info("##########第二种状态切换：新建 -> 运行 -> 等待 -> 运行 -> 终止##########");
        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("thread2 当前状态：{}", Thread.currentThread().getState().toString());
            log.info("thread2 执行了");
        });
        log.info("没有调用 start 方法， thread2 当前状态：{}", thread2.getState().toString());
        thread2.start();
        log.info("调用了 start 方法，thread2 当前状态：{}", thread2.getState().toString());
        Thread.sleep(200L);
        log.info("等待 200 毫秒后，thread2 当前状态：{}", thread2.getState().toString());
        Thread.sleep(3000L);
        log.info("等待 3 秒后，thread2 当前状态：{}", thread2.getState().toString());

        log.info("");
    }

    public static void threadStateChangeTest3() throws InterruptedException {
        // 第三种状态切换：新建 -> 运行 -> 等待 -> 运行 -> 终止
        log.info("##########第三种状态切换：新建 -> 运行 -> 阻塞 -> 运行 -> 终止##########");
        Thread thread3 = new Thread(() -> {
            synchronized (ThreadStateChangeDemo.class) {
                log.info("thread3 当前状态：{}", Thread.currentThread().getState().toString());
                log.info("thread3 执行了");
            }
        });
        synchronized (ThreadStateChangeDemo.class) {
            log.info("没有调用 start 方法， thread3 当前状态：{}", thread3.getState().toString());
            thread3.start();
            log.info("调用了 start 方法，thread3 当前状态：{}", thread3.getState().toString());
            Thread.sleep(200L);
            log.info("等待 200 毫秒后，thread3 当前状态：{}", thread3.getState().toString());
        }
        Thread.sleep(3000L);
        log.info("等待 3 秒后，thread3 当前状态：{}", thread3.getState().toString());
    }

}
