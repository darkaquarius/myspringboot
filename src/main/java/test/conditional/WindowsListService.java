package test.conditional;

/**
 * Created by huishen on 17/6/5.
 *
 */

public class WindowsListService implements ListService{

    @Override
    public String showListCmd() {
        return "dir";
    }
}
