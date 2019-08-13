package cn.org.yxzb.camemt.security.vo;

/**
 * @version V1.0.0
 * <p>
 *     生成 token 所需的信息
 * </p>
 * @author songhao
 * @date  2018.08.13
 */
public interface TokenDetail {

    //TODO: 这里封装了一层，不直接使用 username 做参数的原因是可以方便未来增加其他要封装到 token 中的信息

    String getUsername();
}
