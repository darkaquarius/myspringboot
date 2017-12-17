package boot.service.typetest;

/**
 * Created by huishen on 17/11/17.
 * CustomInterface有2个实现类，CustomeInterfaceImpl1 和 CustomeInterfaceImpl2，
 * 把CustomeInterfaceImpl1 和 CustomeInterfaceImpl2 注入到Spring中，用getBean()去获得
 */
public interface CustomInterface {

    void test();

}
