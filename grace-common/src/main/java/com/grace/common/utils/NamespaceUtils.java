package com.grace.common.utils;

import com.grace.common.constant.Constants;
import com.grace.common.entity.Instance;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static com.grace.common.constant.Constants.CLUSTER_NAME_PATTERN_STRING;
import static com.grace.common.constant.Constants.NUMBER_PATTERN_STRING;

/**
 * NamingUtils.
 *
 * @author nkorange
 * @since 1.0.0
 */
public class NamespaceUtils {
    
    private static final Pattern CLUSTER_NAME_PATTERN = Pattern.compile(CLUSTER_NAME_PATTERN_STRING);
    
    private static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_PATTERN_STRING);
    
    /**
     * Returns a combined string with serviceName and groupName. serviceName can not be nil.
     *
     * <p>etc:
     * <p>serviceName | groupName | result</p>
     * <p>serviceA    | groupA    | groupA@@serviceA</p>
     * <p>nil         | groupA    | threw IllegalArgumentException</p>
     *
     * @return 'groupName@@serviceName'
     */
    public static String getGroupedName(final String serviceName, final String groupName) {
        if (StringUtils.isBlank(serviceName)) {
            throw new IllegalArgumentException("Param 'serviceName' is illegal, serviceName is blank");
        }
        if (StringUtils.isBlank(groupName)) {
            throw new IllegalArgumentException("Param 'groupName' is illegal, groupName is blank");
        }
        final String resultGroupedName = groupName + Constants.SERVICE_INFO_SPLITER + serviceName;
        return resultGroupedName.intern();
    }
    
    public static String getServiceName(final String serviceNameWithGroup) {
        if (StringUtils.isBlank(serviceNameWithGroup)) {
            return StringUtils.EMPTY;
        }
        if (!serviceNameWithGroup.contains(Constants.SERVICE_INFO_SPLITER)) {
            return serviceNameWithGroup;
        }
        return serviceNameWithGroup.split(Constants.SERVICE_INFO_SPLITER)[1];
    }
    
    public static String getGroupName(final String serviceNameWithGroup) {
        if (StringUtils.isBlank(serviceNameWithGroup)) {
            return StringUtils.EMPTY;
        }
        if (!serviceNameWithGroup.contains(Constants.SERVICE_INFO_SPLITER)) {
            return Constants.DEFAULT_GROUP;
        }
        return serviceNameWithGroup.split(Constants.SERVICE_INFO_SPLITER)[0];
    }
    
    /**
     * check combineServiceName format. the serviceName can't be blank.
     * <pre>
     * serviceName = "@@";                 the length = 0; illegal
     * serviceName = "group@@";            the length = 1; illegal
     * serviceName = "@@serviceName";      the length = 2; illegal
     * serviceName = "group@@serviceName"; the length = 2; legal
     * </pre>
     *
     * @param combineServiceName such as: groupName@@serviceName
     */
    public static void checkServiceNameFormat(String combineServiceName) {
        String[] split = combineServiceName.split(Constants.SERVICE_INFO_SPLITER);
        if (split.length <= 1) {
            throw new IllegalArgumentException(
                    "Param 'serviceName' is illegal, it should be format as 'groupName@@serviceName'");
        }
        if (split[0].isEmpty()) {
            throw new IllegalArgumentException("Param 'serviceName' is illegal, groupName can't be empty");
        }
    }
    
    /**
     * Returns a combined string with serviceName and groupName. Such as 'groupName@@serviceName'
     *
     * </p> etc:
     * <p>serviceName | groupName | result</p>
     * <p>serviceA    | groupA    | groupA@@serviceA</p>
     * <p>nil         | groupA    | groupA@@</p>
     * <p>nil         | nil       | @@</p>
     *
     * @return 'groupName@@serviceName'
     */
    public static String getGroupedNameOptional(final String serviceName, final String groupName) {
        return groupName + Constants.SERVICE_INFO_SPLITER + serviceName;
    }
    
//    /**
//     * <p>Check instance param about keep alive.</p>
//     *
//     * <pre>
//     * heart beat timeout must > heart beat interval
//     * ip delete timeout must  > heart beat interval
//     * </pre>
//     *
//     * @param instance need checked instance
//     */
//    public static void checkInstanceIsLegal(Instance instance) {
//        if (instance.getInstanceHeartBeatTimeOut() < instance.getInstanceHeartBeatInterval()
//                || instance.getIpDeleteTimeout() < instance.getInstanceHeartBeatInterval()) {
//            throw new NacosApiException(NacosException.INVALID_PARAM, ErrorCode.INSTANCE_ERROR,
//                    "Instance 'heart beat interval' must less than 'heart beat timeout' and 'ip delete timeout'.");
//        }
//        if (!StringUtils.isEmpty(instance.getClusterName()) && !CLUSTER_NAME_PATTERN.matcher(instance.getClusterName()).matches()) {
//            throw new NacosApiException(NacosException.INVALID_PARAM, ErrorCode.INSTANCE_ERROR,
//                    String.format("Instance 'clusterName' should be characters with only 0-9a-zA-Z-. (current: %s)",
//                            instance.getClusterName()));
//        }
//    }
//
//    /**
//     * check batch register is Ephemeral.
//     * @param instance instance
//     */
//    public static void checkInstanceIsEphemeral(Instance instance) {
//        if (!instance.isEphemeral()) {
//            throw new NacosApiException(NacosException.INVALID_PARAM, ErrorCode.INSTANCE_ERROR,
//                    String.format("Batch registration does not allow persistent instance registration , Instance：%s", instance));
//        }
//    }
//
//    /**
//     * Batch verify the validity of instances.
//     * @param instances List of instances to be registered
//     */
//    public static void batchCheckInstanceIsLegal(List<Instance> instances) {
//        Set<Instance> newInstanceSet = new HashSet<>(instances);
//        for (Instance instance : newInstanceSet) {
//            checkInstanceIsEphemeral(instance);
//            checkInstanceIsLegal(instance);
//        }
//    }
    
    /**
     * Check string is a number or not.
     *
     * @param str a string of digits
     * @return if it is a string of digits, return true
     */
    public static boolean isNumber(String str) {
        return !StringUtils.isEmpty(str) && NUMBER_PATTERN.matcher(str).matches();
    }
}
