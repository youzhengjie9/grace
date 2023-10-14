<template>
  <div class="service-detail-box">
    <el-container style="height: 500px">
      <!-- 页头 -->
      <el-header style="text-align: right; font-size: 12px">
        <page-header />
      </el-header>
      <el-container>
        <!-- 侧边 -->
        <el-aside
          width="200px"
          style="
            border-right: 1px solid #eee;
            background: #fff;
            box-shadow: none;
          "
        >
          <!-- 返回上个页面 -->
          <p style="text-align: center">
            <i class="el-icon-arrow-left" @click="back"></i>
          </p>
        </el-aside>

        <el-container>
          <!-- 右侧内容 -->
          <el-main>
            <service-detail-content
            :serviceDetail="serviceDetail"
            :openModifyServiceDialog="openModifyServiceDialog"
            :modifyServiceForm="modifyServiceForm"
            :modifyServiceRules="modifyServiceRules"
            :openModifyInstanceDialog="openModifyInstanceDialog"
            :modifyInstanceForm="modifyInstanceForm"
            @clickOpenModifyServiceDialog="clickOpenModifyServiceDialog"
            @handleCloseModifyServiceDialog="handleCloseModifyServiceDialog"
            @modifyService="modifyService"
            @clickOpenModifyInstanceDialog="clickOpenModifyInstanceDialog"
            @handleCloseModifyInstanceDialog="handleCloseModifyInstanceDialog"
            @modifyInstance="modifyInstance"
            @offline="offline"
            @online="online"
            />
          </el-main>
        </el-container>
        
      </el-container>
    </el-container>
  </div>
</template>

<script>
import PageHeader from "@/layout/components/PageHeader/index.vue";
import ServiceDetailContent from '.././../../components/service/detail/content/index.vue'
import { getServiceDetail,modifyService } from "@/api/service";

export default {
  name: "ServiceDetail",
  components: {
    PageHeader,
    ServiceDetailContent
  },
  data(){
    return{
      serviceDetail:{},
      // 是否打开修改/编辑服务dialog
      openModifyServiceDialog: false,
      // 修改服务表单
      modifyServiceForm: {},
      // 修改服务表单规则
      modifyServiceRules: {
        // 服务名称
        serviceName: [
          { required: true, message: "请输入服务名称", trigger: "blur" },
        ],
        // 保护阈值（纯整数（123）和小数（567.6512））
        protectThreshold: [
          {
            required: true,
            pattern: /^(\-|\+)?\d+(\.\d+)?$/,
            message: "请输入保护阈值",
            trigger: "blur",
          },
        ],
      },
      // 是否打开修改/编辑实例dialog
      openModifyInstanceDialog: false,
      // 修改/编辑实例的表单内容
      modifyInstanceForm: {
        id: -1,
        ipAddr: "",
        port: -1,
        ephemeral: true,
        weight: -1,
        healthy: false,
        // 是否在线
        online: false,
        metadata: "",
      },
    }
  },
  mounted(){
    // 加载数据
    this.loadData();
  },
  methods: {
    // 加载数据
    loadData(){
      let namespaceId = this.$route.query.namespaceId;
      let groupName = this.$route.query.groupName;
      let serviceName = this.$route.query.serviceName;
      // 调用后端接口从数据库查询该服务详情的数据
      getServiceDetail(namespaceId, groupName, serviceName).then((response) => {
        let result = response.data;

        this.serviceDetail = result.data;
      });
    },
    // 点击打开修改/编辑服务的dialog
    clickOpenModifyServiceDialog() {
      // 打开修改/编辑服务的dialog
      this.openModifyServiceDialog = true;
      // 将服务详情表单（serviceDetail）内容“深拷贝”到一个新的修改/编辑服务的表单（modifyServiceForm）对象中
      this.modifyServiceForm = {
        // namespaceId
        namespaceId: this.serviceDetail.namespaceId,
        // 分组名称
        groupName: this.serviceDetail.groupName,
        // 服务名称
        serviceName: this.serviceDetail.serviceName,
        // 保护阈值
        protectThreshold: this.serviceDetail.protectThreshold,
        // 元数据
        metadata: this.serviceDetail.metadata,
      };
    },
    // 当关闭修改服务dialog的回调
    handleCloseModifyServiceDialog() {
      this.openModifyServiceDialog = false;
    },
    // 修改service
    modifyService(modifyServiceForm){

      // 调用modifyService接口
      modifyService(modifyServiceForm).then((response) =>{
        let result = response.data;
        if(result.data == true){
          this.serviceDetail.protectThreshold=modifyServiceForm.protectThreshold,
          this.serviceDetail.metadata=modifyServiceForm.metadata,
          this.$message.success('修改service成功');
          this.openModifyServiceDialog = false;
        }else{
          this.$message.error('修改service失败');
        }
      })
    },
    // 点击打开修改/编辑实例的dialog
    clickOpenModifyInstanceDialog(row) {
      // metadata对象
      let metadataObject = row.metadata;
      // 将Object转成Map类型
      let metadataMap = new Map(Object.entries(metadataObject));
      // metadataJson对象
      let metadataJSON = "";
      // 如果metadataMap不为空,则格式化成JSON
      if (metadataMap.size > 0) {
        // 将Map转成JSON
        metadataJSON = this.Map2JsonStr(metadataMap);
        // 为了让JSON更好看,所以进行格式化JSON
        metadataJSON = this.formatJsonStr(metadataJSON);
      }

      // 打开修改/编辑实例的dialog
      this.openModifyInstanceDialog = true;

      // 将需要修改的实例对象保存到modifyInstanceForm（修改实例表单内容）,后面我们修改完实例提交这个表单内容即可
      this.modifyInstanceForm = {
        id: row.id,
        ipAddr: row.ipAddr,
        port: row.port,
        ephemeral: row.ephemeral,
        weight: row.weight,
        healthy: row.healthy,
        online: row.online,
        metadata: metadataJSON,
      };
    },
    // 当关闭修改实例dialog的回调
    handleCloseModifyInstanceDialog() {
      this.modifyInstanceForm = {
        id: -1,
        ipAddr: "",
        port: -1,
        ephemeral: true,
        weight: -1,
        healthy: false,
        // 是否在线
        online: false,
        metadata: "",
      };
      this.openModifyInstanceDialog = false;
    },
    // 提交修改实例表单
    modifyInstance() {
      this.$$emit('modifyInstance');
    },
    // 下线
    offline(row) {
      this.$emit('offline',row);
    },
    // 上线
    online(row) {
      this.$emit('online',row);
    },
    back() {
      this.$router.go(-1);
    },
    // 将Map转成JSON字符串
    Map2JsonStr(map) {
      return JSON.stringify(Object.fromEntries(map));
    },
    // 将JSON字符串格式化成用户容易看懂的格式
    formatJsonStr(jsonStr) {
      var result = "";
      var level = 0;
      var i = 0;
      var j = 0;
      var inQuotationMarks = false;
      var currentChar = null;

      for (i = 0; i < jsonStr.length; i++) {
        currentChar = jsonStr.charAt(i);

        switch (currentChar) {
          case "{":
          case "[":
            if (!inQuotationMarks) {
              result += currentChar + "\n" + this.indent(level + 1);
              level++;
            } else {
              result += currentChar;
            }
            break;
          case "}":
          case "]":
            if (!inQuotationMarks) {
              level--;
              result += "\n" + this.indent(level) + currentChar;
            } else {
              result += currentChar;
            }
            break;
          case ",":
            if (!inQuotationMarks) {
              result += ",\n" + this.indent(level);
            } else {
              result += currentChar;
            }
            break;
          case ":":
            if (!inQuotationMarks) {
              result += ": ";
            } else {
              result += currentChar;
            }
            break;
          case '"':
            if (i > 0 && jsonStr.charAt(i - 1) !== "\\") {
              inQuotationMarks = !inQuotationMarks;
            }
            result += currentChar;
            break;
          default:
            result += currentChar;
            break;
        }
      }
      return result;
    },
    indent(level) {
      var result = "";
      var i = 0;
      for (i = 0; i < level; i++) {
        result += "  ";
      }
      return result;
    },
  },
};
</script>

<style scoped>
.el-header {
  background-color: #252a2f;
  color: #fff;
  font-family: Avenir-Medium;
  line-height: 60px;
}
</style>