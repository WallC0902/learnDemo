package com.example.aqs;

/**
 * Created by wangchangpeng on 2019/9/25.
 */
public class BlockQueuen {

    /**
     * 1.offer操作，向队列尾部插入一个元素，如果队列有空闲容量则插入成功后返回true，如果队列已满则丢弃当前元素然后返回false，
     *     如果 e元素为null，则抛出空指针异常(NullPointerException )，还有一点就是，该方法是非阻塞的。
     *  满了直接丢
     *
     * 2.put操作，向队列尾部插入一个元素，如果队列有空闲则插入后直接返回true,如果队列已经满则阻塞当前线程知道队列有空闲插入成功后返回true，
     *      如果在阻塞的时候被其他线程设置了中断标志，
     *  满了还会等着，然后直到写入成功
     *
     * 3.poll操作，从队列头部获取并移除一个元素，如果队列为空则返回 null，该方法是不阻塞的。
     *   获取且移除，队列为空直接返回null
     * 4.peek操作，获取队列头部元素但是不从队列里面移除，如果队列为空则返回 null，该方法是不阻塞的。
     *   获取不移除，队列为空直接返回null
     *
     * 5.take 操作，获取当前队列头部元素并从队列里面移除，如果队列为空则阻塞调用线程。如果队列为空则阻塞当前线程知道队列不为空，
     *      然后返回元素，如果在阻塞的时候被其他线程设置了中断标志，则被阻塞线程会抛出InterruptedException 异常而返回。
     *  获取且移除，队列为空则等待知道有数据，再返回
     *
     * 6.remove操作，删除队列里面指定元素，有则删除返回 true，没有则返回 false
     *  remove时需要获取两把锁，读写锁都要
     *
     *
     * ArrayBlockingQueue和LinkedBlockingQueue的区别：
     *
     * 1.队列大小有所不同，ArrayBlockingQueue是有界的初始化必须指定大小，而LinkedBlockingQueue可以是有界的也可以是无界的
     *      (Integer.MAX_VALUE)，对于后者而言，当添加速度大于移除速度时，在无界的情况下，可能会造成内存溢出等问题。
     *
     * 2.数据存储容器不同，ArrayBlockingQueue采用的是数组作为数据存储容器，而LinkedBlockingQueue采用的则是以Node节点作为连接对象的
     *      链表。
     *
     * 3.由于ArrayBlockingQueue采用的是数组的存储容器，因此在插入或删除元素时不会产生或销毁任何额外的对象实例，而LinkedBlockingQueue
     *      则会生成一个额外的Node对象。这可能在长时间内需要高效并发地处理大批量数据的时，对于GC可能存在较大影响。
     *
     * 4.两者的实现队列添加或移除的锁不一样，ArrayBlockingQueue实现的队列中的锁是没有分离的，即添加操作和移除操作采用的同一个
     *      ReenterLock锁，而LinkedBlockingQueue实现的队列中的锁是分离的，其添加采用的是putLock，移除采用的则是takeLock，
     *      这样能大大提高队列的吞吐量，也意味着在高并发的情况下生产者和消费者可以并行地操作队列中的数据，以此来提高整个队列的并发性能。
     *
     *
     *
     */



}
