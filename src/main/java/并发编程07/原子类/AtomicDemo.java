package 并发编程07.原子类;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.*;

/**
 * @Author linhao
 * @Date created in 8:29 上午 2022/6/22
 */
public class AtomicDemo {

    class Account {
        int id;
        String name;
        volatile int age;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    @Test
    public void testAtomicInteger(){
        AtomicInteger atomicInteger = new AtomicInteger(1);
        System.out.println(atomicInteger.addAndGet(1));
        System.out.println(atomicInteger.getAndAdd(1));
    }

    @Test
    public void testAtomicBoolean(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        atomicBoolean.set(false);
        System.out.println(atomicBoolean.get());
    }


    @Test
    public void testAtomicIntegerArray(){
        int[] value = new int[]{0, 1, 2};
        AtomicIntegerArray ata = new AtomicIntegerArray(value);
        //先指定数组的下标位，在指定需要增加的数值
        ata.addAndGet(0,1);
        System.out.println(ata);
    }

    @Test
    public void testAtomicReference(){
        AtomicReference<String> stringAtomicReference = new AtomicReference<>("idea");
        System.out.println(stringAtomicReference.compareAndSet("idea","idea2"));
        System.out.println(stringAtomicReference.compareAndSet("idea2","idea"));
        System.out.println(stringAtomicReference.compareAndSet("idea","idea2"));
    }


    /**
     * 更新整个对象的类型
     */
    @Test
    public void updateAccount(){
        AtomicReference<Account> accountAtomicReference = new AtomicReference<>();
        Account account = new Account();
        account.setAge(1);
        account.setId(1);
        account.setName("idea");
        accountAtomicReference.set(account);
        Account account2 = new Account();
        account2.setAge(2);
        account2.setId(2);
        account2.setName("idea2");
        accountAtomicReference.compareAndSet(account,account2);
        System.out.println(accountAtomicReference.get().getAge());
        System.out.println(accountAtomicReference.get().getId());
        System.out.println(accountAtomicReference.get().getName());
    }

    /**
     * 更新指定对象的某个字段
     */
    @Test
    public void updateAccountField(){
        Account account = new Account();
        account.setAge(1);
        account.setId(1);
        account.setName("idea");
        //age字段一定要为volatile类型
        AtomicIntegerFieldUpdater<Account> accountAtomicReference = AtomicIntegerFieldUpdater.newUpdater(Account.class,"age");
        Account account2 = new Account();
        account2.setAge(2);
        account2.setId(2);
        account2.setName("idea2");
        //这里输出的数值中，age会加2
        System.out.println(accountAtomicReference.incrementAndGet(account2));

    }
}
