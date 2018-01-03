这些序列化接口是抄java的, data和object的序列化和反序列化都是java定义的,定义的包位于  
  * import java.io.ObjectInput;    
  * import java.io.DataInput;  
  * import java.io.ObjectOutput;  
  * import java.io.DataOutput;  

serialize，数据序列化层，可复用的一些工具，扩展接口为Serialization, ObjectInput, ObjectOutput, ThreadPool
serialize层放在common模块中，以便更大程度复用。

扩展示例：
Maven项目结构
src
 |-main
    |-java
        |-com
            |-xxx
                |-XxxSerialization.java (实现Serialization接口)
                |-XxxObjectInput.java (实现ObjectInput接口)
                |-XxxObjectOutput.java (实现ObjectOutput接口)
    |-resources
        |-META-INF
            |-dubbo
                |-com.alibaba.dubbo.common.serialize.Serialization (纯文本文件，内容为：xxx=com.xxx.XxxSerialization)  
                
代码
===

package com.xxx;
 
import com.alibaba.dubbo.common.serialize.Serialization;
import com.alibaba.dubbo.common.serialize.ObjectInput;
import com.alibaba.dubbo.common.serialize.ObjectOutput;
 
 
public class XxxSerialization implements Serialization {
    public ObjectOutput serialize(Parameters parameters, OutputStream output) throws IOException {
        return new XxxObjectOutput(output);
    }
    public ObjectInput deserialize(Parameters parameters, InputStream input) throws IOException {
        return new XxxObjectInput(input);
    }
}


META-INF/dubbo/com.alibaba.dubbo.common.serialize.Serialization  
===============================================================
xxx=com.xxx.XxxSerialization                