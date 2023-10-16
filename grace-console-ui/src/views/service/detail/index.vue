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
              :metadataFilterInput="metadataFilterInput"
              :metadataFilterTags="metadataFilterTags"
              :openModifyInstanceDialog="openModifyInstanceDialog"
              :modifyInstanceForm="modifyInstanceForm"
              :instanceTableData="instanceTableData"
              :instanceTableLoading="instanceTableLoading"
              @clickOpenModifyServiceDialog="clickOpenModifyServiceDialog"
              @handleCloseModifyServiceDialog="handleCloseModifyServiceDialog"
              @modifyService="modifyService"
              @addMetadataFilterCondition="addMetadataFilterCondition"
              @clearMetadataFilterCondition="clearMetadataFilterCondition"
              @deleteMetadataFilterTagByKey="deleteMetadataFilterTagByKey"
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
import ServiceDetailContent from ".././../../components/service/detail/content/index.vue";
import { getServiceDetail, modifyService } from "@/api/service";
import { modifyInstance } from "@/api/instance";

export default {
  name: "ServiceDetail",
  components: {
    PageHeader,
    ServiceDetailContent,
  },
  data() {
    return {
      serviceDetail: {},
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
      // 元数据过滤输入框内容
      metadataFilterInput: {
        // 元数据过滤输入框的key
        key: "",
        // 元数据过滤输入框的value
        value: "",
      },
      // 元数据过滤条件标签数组
      metadataFilterTags: [],
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
      // 展示实例的表格数据
      instanceTableData: [],
      // 展示实例表格是否加载中（ true说明表格正在加载中,则会显示加载动画。反之false则关闭加载动画）
      instanceTableLoading: false,
    };
  },
  mounted() {
    // 加载数据
    this.loadData();
  },
  methods: {
    // 加载数据
    loadData() {
      
      let namespaceId = this.$route.query.namespaceId;
      let groupName = this.$route.query.groupName;
      let serviceName = this.$route.query.serviceName;

      // 注意: 将展示实例的表格数据清空,以免其他方法调用loadData()时
      // 由于“this.instanceTableData.push(newInstance);”方法导致数据变多的bug
      this.instanceTableData = [];
      // 开启展示实例的表格的加载动画
      this.instanceTableLoading = true;

      // 调用后端接口从数据库查询该服务详情的数据
      getServiceDetail(namespaceId, groupName, serviceName).then((response) => {
        let result = response.data;

        this.serviceDetail = result.data;

        // 模拟延迟,让加载动画更明显
        setTimeout(() => {
          // 将服务详情表单（serviceDetail）内容复制到一个新的对象中
          let allInstances = this.serviceDetail.allInstances;

          for (let i = 0; i < allInstances.length; i++) {
            let instance = allInstances[i];
            // 深拷贝实例对象
            let newInstance = this.deepCopy(instance);
            // 将深拷贝出来的新对象放到instanceTableData集合中
            this.instanceTableData.push(newInstance);
          }
          //
          this.instanceTableLoading = false;
        }, 500);
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
    modifyService(modifyServiceForm) {
      // 调用modifyService接口
      modifyService(modifyServiceForm).then((response) => {
        let result = response.data;
        if (result.data == true) {
          (this.serviceDetail.protectThreshold =
            modifyServiceForm.protectThreshold),
            (this.serviceDetail.metadata = modifyServiceForm.metadata),
            this.$message.success("修改服务成功");
          this.openModifyServiceDialog = false;
        } else {
          this.$message.error("修改服务失败");
        }
      });
    },
    // 添加元数据过滤条件
    addMetadataFilterCondition() {
      // 元数据过滤输入框内容
      let metadataFilterInput = this.metadataFilterInput;
      // 是否允许添加该元数据过滤条件
      let allowAddMetadataFilterCondition = true;
      // 判断元数据过滤条件标签数组是否已经存在元数据过滤输入框内容的key
      for (let i = 0; i < this.metadataFilterTags.length; i++) {
        let metadataFilterTag = this.metadataFilterTags[i];
        // 如果元数据过滤条件标签数组是否已经存在元数据过滤输入框内容的key
        if (metadataFilterTag.key == metadataFilterInput.key) {
          // 标记为不允许添加该元数据过滤条件
          allowAddMetadataFilterCondition = false;
          break;
        }
      }

      // 如果不允许添加该元数据过滤条件
      if (allowAddMetadataFilterCondition == false) {
        this.$message({
          message: "该过滤条件的key已存在,不允许重复添加",
          type: "warning",
        });
      }
      // 如果允许添加该元数据过滤条件
      else {
        // 将元数据过滤输入框的内容放到元数据过滤条件标签数组中
        this.metadataFilterTags.push(this.metadataFilterInput);
        // 清空元数据过滤输入框的内容
        this.metadataFilterInput = {
          // 元数据过滤输入框的key
          key: "",
          // 元数据过滤输入框的value
          value: "",
        };
        let len = this.instanceTableData.length;
        // 根据过滤条件筛选数据并更新instanceTableData
        for (let i = 0; i < len; i++) {
          let instance = this.instanceTableData[i];
          // 取出metadata对象
          let metadataObject = instance.metadata;
          // 将Object转成Map
          let metadataMap = new Map(Object.entries(metadataObject));
          // 如果metadataMap没有数据则break，说明该条实例instance不会被展示
          if (metadataMap.size == 0) {
            // 移除当前instance（此时this.instanceTableData的length会 -1）
            this.instanceTableData.splice(i, 1);
            // 由于此时调用了splice方法,所以len-1（数组长度-1）,i(索引 - 1),否则会报错
            len--;
            i--;
          }
          // 如果metadataMap有数据则进行条件过滤
          else {
            // metadata过滤条件标签key
            let metadataFilterTagKey = metadataFilterInput.key;
            // metadata过滤条件标签value
            let metadataFilterTagValue = metadataFilterInput.value;
            // 如果metadataMap不存在该metadata过滤条件标签key则break
            if (!metadataMap.has(metadataFilterTagKey)) {
              // 移除当前instance
              this.instanceTableData.splice(i, 1);
              // 由于此时调用了splice方法,所以len-1（数组长度-1）,i(索引 - 1),否则会报错
              len--;
              i--;
            } else {
              // 通过该metadata过滤条件标签key获取metadataMap中对应的value
              let value = metadataMap.get(metadataFilterTagKey);
              // 将value和metadataFilterTagValue进行对比,如果不一样则break
              if (value != metadataFilterTagValue) {
                // 移除当前instance
                this.instanceTableData.splice(i, 1);
                // 由于此时调用了splice方法,所以len-1（数组长度-1）, i-1 (索引 - 1),否则会报错
                len--;
                i--;
              }
            }
          }
        }
      }
    },
    // 清空所有元数据过滤条件
    clearMetadataFilterCondition() {
      // 将元数据过滤条件标签数组中的所有元素清空
      this.metadataFilterTags = [];
      // 注意: 将展示实例的表格数据清空,以免其他方法调用loadData()时
      // 由于“this.instanceTableData.push(newInstance);”方法导致数据变多的bug
      this.instanceTableData = [];
      //
      this.instanceTableLoading = true;
      // 模拟延迟,让加载动画更明显
      setTimeout(() => {
        // 还原展示实例表格数据
        // 将服务详情表单（serviceDetail）内容复制到一个新的对象中
        let allInstances = this.serviceDetail.allInstances;

        for (let i = 0; i < allInstances.length; i++) {
          let instance = allInstances[i];
          // 深拷贝实例对象
          let newInstance = this.deepCopy(instance);

          // 将深拷贝出来的新对象放到instanceTableData集合中
          this.instanceTableData.push(newInstance);
        }
        //
        this.instanceTableLoading = false;
      }, 500);
    },
    // 根据元数据的key删除元数据过滤条件标签
    deleteMetadataFilterTagByKey(deleteMetedataKey) {
      // 1: 删除指定的元数据过滤条件标签
      let len = this.metadataFilterTags.length;
      for (let i = 0; i < len; i++) {
        let tag = this.metadataFilterTags[i];
        // 通过对比key找到我们想删除的元素
        if (tag.key == deleteMetedataKey) {
          // 删除元素
          this.metadataFilterTags.splice(i, 1);
          // 退出循环
          break;
        }
      }
      // 2: 将剩下的所有元数据过滤条件标签再进行过滤数据

      // 获取该service下面的所有实例,并深拷贝出来
      let instances = this.deepCopy(this.serviceDetail.allInstances);
      // 元数据过滤条件标签的数组
      let metadataFilterTags = this.metadataFilterTags;
      // 遍历当前最新的元数据过滤条件标签的数组(原理是: 将元数据过滤条件一个个取出来然后进行筛选)
      for (let i = 0; i < metadataFilterTags.length; i++) {
        // 取出每一个元数据过滤条件标签
        let metadataFilterTag = metadataFilterTags[i];
        // 该service下面的所有实例的总数
        let instancesLength = instances.length;
        // 根据过滤条件筛选数据并更新instanceTableData
        for (let j = 0; j < instancesLength; j++) {
          let instance = instances[j];
          // 取出metadata对象
          let metadataObject = instance.metadata;
          // 将Object转成Map
          let metadataMap = new Map(Object.entries(metadataObject));
          // 如果metadataMap没有数据则break，说明该条实例instance不会被展示
          if (metadataMap.size == 0) {
            // 移除当前instance（此时instances的length会 -1）
            instances.splice(j, 1);
            // 由于此时调用了splice方法,所以instancesLength-1（数组长度-1）, j-1(索引 - 1),否则会报错
            instancesLength--;
            j--;
          }
          // 如果metadataMap有数据则进行条件过滤
          else {
            // metadata过滤条件标签key
            let metadataFilterTagKey = metadataFilterTag.key;
            // metadata过滤条件标签value
            let metadataFilterTagValue = metadataFilterTag.value;
            // 如果metadataMap不存在该metadata过滤条件标签key则break
            if (!metadataMap.has(metadataFilterTagKey)) {
              // 移除当前instance（此时instances的length会 -1）
              instances.splice(j, 1);
              // 由于此时调用了splice方法,所以instancesLength-1（数组长度-1）, j-1(索引 - 1),否则会报错
              instancesLength--;
              j--;
            } else {
              // 通过该metadata过滤条件标签key获取metadataMap中对应的value
              let value = metadataMap.get(metadataFilterTagKey);
              // 将value和metadataFilterTagValue进行对比,如果不一样则break
              if (value != metadataFilterTagValue) {
                // 移除当前instance（此时instances的length会 -1）
                instances.splice(j, 1);
                // 由于此时调用了splice方法,所以instancesLength-1（数组长度-1）, j-1(索引 - 1),否则会报错
                instancesLength--;
                j--;
              }
            }
          }
        }
      }
      // 将筛选好的实例表格数据赋值给this.instanceTableData对象进行展示
      this.instanceTableData = instances;
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
        instanceId: row.instanceId,
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
        instanceId: "0",
        ipAddr: "",
        port: 0,
        ephemeral: true,
        weight: 0,
        healthy: false,
        // 是否在线
        online: false,
        metadata: "",
      };
      this.openModifyInstanceDialog = false;
    },
    // 提交修改实例表单
    modifyInstance() {
      let modifyInstanceDTO = {
        namespaceId: this.serviceDetail.namespaceId,
        groupName: this.serviceDetail.groupName,
        serviceName: this.serviceDetail.serviceName,
        instanceId: this.modifyInstanceForm.instanceId,
        weight: this.modifyInstanceForm.weight,
        healthy: this.modifyInstanceForm.healthy,
        online: this.modifyInstanceForm.online,
        metadata: this.modifyInstanceForm.metadata,
      };
      // 调用接口
      modifyInstance(modifyInstanceDTO).then((response) => {
        let result = response.data;
        if (result.data == true) {
          this.$message.success("修改实例成功");
          // 重新加载数据（特别是重新加载展示实例的表格数据）
          this.loadData();
          this.openModifyInstanceDialog = false;
        } else {
          this.$message.error("修改实例失败");
        }
      });
    },
    // 下线
    offline(row) {
      //
      this.instanceTableLoading = true;

      // 这里的metadata是Object类型的数据，将其转成Map类型的数据
      let metadataMap = new Map(Object.entries(row.metadata));
      // metadata的Json字符串类型的数据
      let metadataJson;
      // 如果metadataMap没有数据
      if (metadataMap.size == 0) {
        metadataJson = "";
      }
      // 如果metadataMap有数据
      else {
        // 转成JSON
        metadataJson = this.Map2JsonStr(metadataMap);
        // 将JSON字符串格式化成用户容易看懂的格式
        metadataJson = this.formatJsonStr(metadataJson);
      }

      let modifyInstanceDTO = {
        namespaceId: this.serviceDetail.namespaceId,
        groupName: this.serviceDetail.groupName,
        serviceName: this.serviceDetail.serviceName,
        instanceId: row.instanceId,
        weight: row.weight,
        healthy: row.healthy,
        online: false,
        metadata: metadataJson,
      };
      // 模拟延迟,让加载动画更明显
      setTimeout(() => {
        modifyInstance(modifyInstanceDTO).then((response) => {
          let result = response.data;
          if (result.data == true) {
            this.$message.success("下线成功");
            row.online = false;
            //
            this.instanceTableLoading = false;
          } else {
            this.$message.error("下线失败");
          }
        });
      }, 500);
      // 调用接口
    },
    // 上线
    online(row) {
      //
      this.instanceTableLoading = true;
      // 这里的metadata是Object类型的数据，将其转成Map类型的数据
      let metadataMap = new Map(Object.entries(row.metadata));
      // metadata的Json字符串类型的数据
      let metadataJson;
      // 如果metadataMap没有数据
      if (metadataMap.size == 0) {
        metadataJson = "";
      }
      // 如果metadataMap有数据
      else {
        // 转成JSON
        metadataJson = this.Map2JsonStr(metadataMap);
        // 将JSON字符串格式化成用户容易看懂的格式
        metadataJson = this.formatJsonStr(metadataJson);
      }

      let modifyInstanceDTO = {
        namespaceId: this.serviceDetail.namespaceId,
        groupName: this.serviceDetail.groupName,
        serviceName: this.serviceDetail.serviceName,
        instanceId: row.instanceId,
        weight: row.weight,
        healthy: row.healthy,
        online: true,
        metadata: metadataJson,
      };

      // 模拟延迟,让加载动画更明显
      setTimeout(() => {
        // 调用接口
        modifyInstance(modifyInstanceDTO).then((response) => {
          let result = response.data;
          if (result.data == true) {
            this.$message.success("上线成功");
            row.online = true;
            //
            this.instanceTableLoading = false;
          } else {
            this.$message.error("上线失败");
          }
        });
      }, 500);
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
    // 深拷贝(将obj对象进行深拷贝,返回值就是深拷贝出来的对象)
    deepCopy(obj) {
      // 判断是否是对象
      if (typeof obj !== "object") return;
      // 判断obj类型，根据类型新建一个对象或者数组
      var newObj = obj instanceof Array ? [] : {};
      // 遍历对象，进行赋值
      for (var key in obj) {
        if (obj.hasOwnProperty(key)) {
          let val = obj[key];
          // 判断属性值的类型，如果是对象，递归调用deepCopy
          newObj[key] = typeof val === "object" ? this.deepCopy(val) : val;
        }
      }
      return newObj;
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