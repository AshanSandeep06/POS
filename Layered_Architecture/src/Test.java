public class Test {
    public static void main(String[] args){
        run("SELECT * FROM Customer","1001","Ashan",21);
    }

    // Object type Var-args
    // Optional Parameters
    private static void run(String sql, Object... params) {
        System.out.println(sql);
        for (Object param : params) {
            System.out.println(param);
        }
    }
}
