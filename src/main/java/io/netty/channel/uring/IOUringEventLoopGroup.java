/*
 * Copyright 2020 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.channel.uring;

import io.netty.channel.DefaultSelectStrategyFactory;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopTaskQueueFactory;
import io.netty.channel.MultithreadEventLoopGroup;
import io.netty.channel.SelectStrategyFactory;
import io.netty.util.concurrent.EventExecutorChooserFactory;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.concurrent.RejectedExecutionHandlers;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

public final class IOUringEventLoopGroup extends MultithreadEventLoopGroup {

    /**
     * Create a new instance using the default number of threads and the default {@link ThreadFactory}.
     */
    public IOUringEventLoopGroup() {
        this(0);
    }

    /**
     * Create a new instance using the specified number of threads and the default {@link ThreadFactory}.
     */
    public IOUringEventLoopGroup(int nThreads) {
        this(nThreads, (ThreadFactory) null);
    }

    /**
     * Create a new instance using the default number of threads and the given {@link ThreadFactory}.
     */
    @SuppressWarnings("deprecation")
    public IOUringEventLoopGroup(ThreadFactory threadFactory) {
        this(0, threadFactory, 0);
    }

    /**
     * Create a new instance using the specified number of threads and the default {@link ThreadFactory}.
     */
    @SuppressWarnings("deprecation")
    public IOUringEventLoopGroup(int nThreads, SelectStrategyFactory selectStrategyFactory) {
        this(nThreads, (ThreadFactory) null, selectStrategyFactory);
    }

    /**
     * Create a new instance using the specified number of threads and the given {@link ThreadFactory}.
     */
    @SuppressWarnings("deprecation")
    public IOUringEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
        this(nThreads, threadFactory, 0);
    }

    public IOUringEventLoopGroup(int nThreads, Executor executor) {
        this(nThreads, executor, DefaultSelectStrategyFactory.INSTANCE);
    }

    /**
     * Create a new instance using the specified number of threads and the given {@link ThreadFactory}.
     */
    @SuppressWarnings("deprecation")
    public IOUringEventLoopGroup(int nThreads, ThreadFactory threadFactory,
                                 SelectStrategyFactory selectStrategyFactory) {
        this(nThreads, threadFactory, 0, selectStrategyFactory);
    }

    /**
     * Create a new instance using the specified number of threads, the given {@link ThreadFactory} and the given
     * maximal amount of epoll events to handle per epollWait(...).
     *
     * @deprecated Use {@link #IOUringEventLoopGroup(int)} or {@link #IOUringEventLoopGroup(int, ThreadFactory)}
     */
    @Deprecated
    public IOUringEventLoopGroup(int nThreads, ThreadFactory threadFactory, int maxEventsAtOnce) {
        this(nThreads, threadFactory, maxEventsAtOnce, DefaultSelectStrategyFactory.INSTANCE);
    }

    /**
     * Create a new instance using the specified number of threads, the given {@link ThreadFactory} and the given
     * maximal amount of epoll events to handle per epollWait(...).
     *
     * @deprecated Use {@link #IOUringEventLoopGroup(int)}, {@link #IOUringEventLoopGroup(int, ThreadFactory)}, or
     * {@link #IOUringEventLoopGroup(int, SelectStrategyFactory)}
     */
    @Deprecated
    public IOUringEventLoopGroup(int nThreads, ThreadFactory threadFactory, int maxEventsAtOnce,
                                 SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, threadFactory, maxEventsAtOnce, selectStrategyFactory, RejectedExecutionHandlers.reject());
    }

    public IOUringEventLoopGroup(int nThreads, Executor executor, SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, executor, 0, selectStrategyFactory, RejectedExecutionHandlers.reject());
    }

    public IOUringEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory,
                                 SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, executor, chooserFactory, 0, selectStrategyFactory, RejectedExecutionHandlers.reject());
    }

    public IOUringEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory,
                                 SelectStrategyFactory selectStrategyFactory,
                                 RejectedExecutionHandler rejectedExecutionHandler) {
        super(nThreads, executor, chooserFactory, 0, selectStrategyFactory, rejectedExecutionHandler);
    }

    public IOUringEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory,
                                 SelectStrategyFactory selectStrategyFactory,
                                 RejectedExecutionHandler rejectedExecutionHandler,
                                 EventLoopTaskQueueFactory queueFactory) {
        super(nThreads, executor, chooserFactory, 0,
              selectStrategyFactory, rejectedExecutionHandler, queueFactory);
    }

    /**
     * @deprecated This method will be removed in future releases, and is not guaranteed to have any impacts.
     */
    @Deprecated
    public void setIoRatio(int ioRatio) {
        if (ioRatio <= 0 || ioRatio > 100) {
            throw new IllegalArgumentException("ioRatio: " + ioRatio + " (expected: 0 < ioRatio <= 100)");
        }
    }

    //Todo
    @Override
    protected EventLoop newChild(Executor executor, Object... args) throws Exception {
     //EventLoopTaskQueueFactory queueFactory = args.length == 4? (EventLoopTaskQueueFactory) args[3] : null;
//        return new IOUringEventLoop(this, executor, (Integer) args[0],
//                ((SelectStrategyFactory) args[1]).newSelectStrategy(),
//                (RejectedExecutionHandler) args[2], queueFactory);

        return new IOUringEventLoop(this, executor, false);
    }
}
