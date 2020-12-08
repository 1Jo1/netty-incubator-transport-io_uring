/*
 * Copyright 2020 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.incubator.channel.uring;


final class RingBuffer {
    private final IOUringSubmissionQueue ioUringSubmissionQueue;
    private final IOUringCompletionQueue ioUringCompletionQueue;

    static final Object lock = new Object();

    RingBuffer(IOUringSubmissionQueue ioUringSubmissionQueue, IOUringCompletionQueue ioUringCompletionQueue) {
        this.ioUringSubmissionQueue = ioUringSubmissionQueue;
        this.ioUringCompletionQueue = ioUringCompletionQueue;

        System.out.println("Create RingBuffer : " + ioUringCompletionQueue.ringFd);
    }

    int fd() {
        return ioUringCompletionQueue.ringFd;
    }

    IOUringSubmissionQueue ioUringSubmissionQueue() {
        return this.ioUringSubmissionQueue;
    }

    IOUringCompletionQueue ioUringCompletionQueue() {
        return this.ioUringCompletionQueue;
    }

    void close() {
        System.out.println("Close RingBuffer");
        ioUringSubmissionQueue.release();
        Native.ioUringExit(
                ioUringSubmissionQueue.submissionQueueArrayAddress,
                ioUringSubmissionQueue.ringEntries,
                ioUringSubmissionQueue.ringAddress,
                ioUringSubmissionQueue.ringSize,
                ioUringCompletionQueue.ringAddress,
                ioUringCompletionQueue.ringSize,
                ioUringCompletionQueue.ringFd);
    }
}
