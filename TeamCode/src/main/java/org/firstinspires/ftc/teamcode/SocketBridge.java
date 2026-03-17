package org.firstinspires.ftc.teamcode;

import java.io.*;
import java.net.*;
import java.lang.reflect.*;

public class SocketBridge
{

    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(5000);
        System.out.println("Lua bridge running on port 5000");

        while(true) {

            Socket client = server.accept();

            System.out.println("accepted client");
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(client.getInputStream()));

            System.out.println("in created");
            PrintWriter out =
                    new PrintWriter(client.getOutputStream(), true);

            System.out.println("out created");
            out.println("READY");
            String line;

            while((line = in.readLine()) != null) {
                System.out.println("Received: " + line);
                if(line.equals("exit"))
                    break;
                if(line.equals("ping")) {
                    out.println("pong");
                    continue;
                }

                try {

                    String[] parts = line.split(" ");

                    String className = parts[0];
                    String methodName = parts[1];

                    int argCount = parts.length - 2;

                    Object[] methodArgs = new Object[argCount];
                    Class<?>[] types = new Class<?>[argCount];

                    for(int i=0;i<argCount;i++){
                        double val = Double.parseDouble(parts[i+2]);
                        methodArgs[i] = val;
                        types[i] = double.class;
                    }

                    Class<?> clazz = Class.forName(className);
                    Method method = clazz.getMethod(methodName, types);

                    Object result = method.invoke(null, methodArgs);

                    out.println(getString(result));

                } catch(Exception e) {
                    out.println("ERROR: " + e.toString());
                }

            }

            client.close();
        }
    }
    public static String getString(Object o){
        if(o instanceof double[]) {

            double[] arr = (double[]) o;
            String string = "";
            for(double v : arr) {
                string +=(v + " ");
            }
            //return Arrays.toString((double[]) o);
            return string;
        }
        else return o.toString();
    }
}