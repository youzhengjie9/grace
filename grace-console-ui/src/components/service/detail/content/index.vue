<template>
  <div class="service-detail-content">
    <!-- 标题 -->
    <el-row :gutter="24">
      <el-col :span="6">
        <span style="font-size: 28px; height: 40px; font-weight: 500"
          >服务详情</span
        >
      </el-col>
      <el-col :span="5" :offset="13">
        <span style="font-size: 28px; height: 40px; font-weight: 500">
          <el-button
            size="medium"
            style="margin-right: 10px"
            @click="clickOpenModifyServiceDialog"
            >编辑服务</el-button
          >
          <el-button type="primary" size="medium" @click="back">返回</el-button>
        </span>
      </el-col>
    </el-row>

    <!-- 服务详情展示(不能进行编辑)  -->
    <el-form :model="serviceForm" label-width="150px">
      <el-form-item label="服务名称">
        <el-input
          v-model="serviceForm.serviceName"
          autocomplete="off"
          style="width: 70%"
          :disabled="true"
        ></el-input>
      </el-form-item>
      <el-form-item label="分组名称">
        <el-input
          v-model="serviceForm.groupName"
          autocomplete="off"
          placeholder="DEFAULT_GROUP"
          style="width: 70%"
          :disabled="true"
        ></el-input>
      </el-form-item>
      <el-form-item label="保护阈值">
        <el-input
          v-model="serviceForm.protectionThreshold"
          autocomplete="off"
          style="width: 70%"
          :disabled="true"
        ></el-input>
      </el-form-item>
      <el-form-item label="元数据">
        <!-- 代码编辑器 -->
        <editor
          v-model="serviceForm.metadata"
          lang="json"
          theme="tomorrow_night"
          width="70%"
          height="320"
          @init="editorInit"
          :options="readOnlyEditorOptions"
        ></editor>
      </el-form-item>
    </el-form>

    <!-- 编辑服务dialog -->
    <el-dialog
      title="编辑服务"
      :visible.sync="openModifyServiceDialog"
      @close="handleCloseModifyServiceDialog"
    >
      <el-form
        :model="modifyServiceForm"
        :rules="modifyServiceRules"
        label-width="90px"
      >
        <el-form-item prop="serviceName">
          <!-- 标题 -->
          <span slot="label">
            <span style="font-weight: bold">服务名称</span>
          </span>
          <el-col :span="20">
            <el-input
              v-model="modifyServiceForm.serviceName"
              autocomplete="off"
            ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item prop="groupName">
          <!-- 标题 -->
          <span slot="label">
            <span style="font-weight: bold">分组名称</span>
          </span>
          <el-col :span="20">
            <el-input
              v-model="modifyServiceForm.groupName"
              autocomplete="off"
              placeholder="DEFAULT_GROUP"
            ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item prop="protectionThreshold">
          <!-- 标题 -->
          <span slot="label">
            <span style="font-weight: bold">保护阈值</span>
          </span>
          <el-col :span="20">
            <el-input
              v-model="modifyServiceForm.protectionThreshold"
              autocomplete="off"
            ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item prop="metadata">
          <!-- 标题 -->
          <span slot="label">
            <span style="font-weight: bold">元数据</span>
          </span>
          <!-- 代码编辑器 -->
          <editor
            v-model="modifyServiceForm.metadata"
            lang="json"
            theme="tomorrow_night"
            width="85%"
            height="320"
            @init="editorInit"
            :options="writableEditorOptions"
          ></editor>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="modifyService">确定</el-button>
        <el-button @click="handleCloseModifyServiceDialog">取消</el-button>
      </div>
    </el-dialog>
    s
    <!-- 集群 -->
    <el-row :gutter="24">
      <el-col :span="24">
        <div class="next-card-title">服务实例</div>
      </el-col>
      <!-- <el-col :span="3" :offset="14">
        <span style="font-size: 28px; height: 40px; font-weight: 500">
          <el-button size="medium" style="margin-right: 10px"
            >集群配置</el-button
          >
        </span>
      </el-col> -->
    </el-row>

    <!-- 元数据过滤 -->
    <el-row
      :gutter="24"
      style="margin-top: 20px; margin-left: 1px; margin-bottom: 30px"
    >
      <el-col :span="12">
        <span style="font-size: 12px; font-weight: 800; color: #666"
          >元数据过滤</span
        >
        <el-input
          v-model="metadataFilterInput.key"
          placeholder="key"
          size="mini"
          style="width: 130px; margin-left: 10px; margin-right: 15px"
        ></el-input>

        <el-input
          v-model="metadataFilterInput.value"
          placeholder="value"
          size="mini"
          style="width: 130px; margin-right: 15px"
        ></el-input>

        <el-button
          type="primary"
          size="mini"
          @click="addMetadataFilterCondition"
          >添加过滤</el-button
        >
        <!-- 如果元数据过滤条件标签数组不为空则显示这个按钮 -->
        <el-button
          v-if="metadataFilterTags.length > 0"
          type="primary"
          size="mini"
          @click="clearMetadataFilterCondition"
          >清空</el-button
        >
      </el-col>
    </el-row>

    <!-- 元数据的过滤条件展示 -->
    <div
      class="metadataFilterTags"
      style="margin-left: 10px; margin-bottom: 50px"
    >
      <el-tag
        v-for="tag in metadataFilterTags"
        :key="tag.key"
        closable
        style="margin-right: 10px"
        @close="deleteMetadataFilterTagByKey(tag.key)"
      >
        {{ tag.key + " : " + tag.value }}
      </el-tag>
    </div>

    <!-- 展示实例的表格 -->
    <el-table :data="tableData" border style="width: 100%; margin-bottom: 20%">
      <el-table-column prop="ipAddr" label="IP" width="138"> </el-table-column>
      <el-table-column prop="port" label="端口" width="80"> </el-table-column>
      <el-table-column
        prop="ephemeral"
        :formatter="formatEphemeral"
        label="临时实例"
        width="100"
      >
      </el-table-column>
      <el-table-column prop="weight" label="权重" width="100">
      </el-table-column>

      <el-table-column label="健康状态" width="100">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.healthy == true">
            健康
          </el-tag>

          <el-tag type="danger" v-if="scope.row.healthy == false">
            不健康
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="是否在线" width="100">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.online == true"> 在线 </el-tag>

          <el-tag type="danger" v-if="scope.row.online == false">
            不在线
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="元数据" width="470">
        <template slot-scope="scope">
          <!-- metadata渲染成html -->
          <div v-html="formatMetadata(scope.row)"></div>
        </template>
      </el-table-column>

      <el-table-column label="操作" min-width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="clickOpenModifyInstanceDialog(scope.row)"
            >编辑</el-button
          >

          <!-- 下线 -->
          <el-button
            v-if="scope.row.online == true"
            size="mini"
            type="danger"
            @click="offline(scope.row)"
            >下线</el-button
          >

          <!-- 上线 -->
          <el-button
            v-if="scope.row.online == false"
            size="mini"
            type="success"
            @click="online(scope.row)"
            >上线</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 编辑实例 -->
    <el-dialog
      title="编辑实例"
      :visible.sync="openModifyInstanceDialog"
      @close="handleCloseModifyInstanceDialog"
    >
      <el-form :model="modifyInstanceForm" label-width="90px">
        <el-form-item>
          <!-- 标题 -->
          <span slot="label">
            <span style="font-weight: bold">IP</span>
          </span>
          <el-col :span="20">
            <el-input
              v-model="modifyInstanceForm.ipAddr"
              autocomplete="off"
              :disabled="true"
            ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item>
          <!-- 标题 -->
          <span slot="label">
            <span style="font-weight: bold">端口</span>
          </span>
          <el-col :span="20">
            <el-input
              v-model="modifyInstanceForm.port"
              autocomplete="off"
              :disabled="true"
            ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item>
          <!-- 标题 -->
          <span slot="label">
            <span style="font-weight: bold">权重</span>
          </span>
          <el-col :span="20">
            <el-input
              v-model="modifyInstanceForm.weight"
              autocomplete="off"
            ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item>
          <!-- 标题 -->
          <span slot="label">
            <span style="font-weight: bold">是否上线</span>
          </span>
          <el-switch
            v-model="modifyInstanceForm.online"
            active-color="#209bfa"
            inactive-color="#f5f5f5"
          >
          </el-switch>
        </el-form-item>
        <el-form-item prop="metadata">
          <!-- 标题 -->
          <span slot="label">
            <span style="font-weight: bold">元数据</span>
          </span>
          <!-- 代码编辑器 -->
          <editor
            v-model="modifyInstanceForm.metadata"
            lang="json"
            theme="tomorrow_night"
            width="85%"
            height="320"
            @init="editorInit"
            :options="writableEditorOptions"
          ></editor>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="modifyInstance">确定</el-button>
        <el-button @click="handleCloseModifyInstanceDialog">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// 引入vue2-ace-editor代码编辑器
import Editor from "vue2-ace-editor";

export default {
  name: "ServiceDetailContent",
  components: {
    Editor,
  },
  data() {
    return {
      // 服务详情展示表单
      serviceForm: {
        // 服务id
        id: -1,
        // 服务名称
        serviceName: "",
        // 分组名称
        groupName: "",
        // 保护阈值
        protectionThreshold: "",
        // 元数据
        metadata: "",
      },
      // 只读的（不能进行编辑的）代码编辑器配置
      readOnlyEditorOptions: {
        // 是否只读
        readOnly: true,
        // 启用代码段
        enableSnippets: true,
        // 高亮配置
        highlightActiveLine: true,
        //标签大小
        tabSize: 2,
        //设置字号
        fontSize: 18,
        //去除编辑器里的竖线
        showPrintMargin: false,
        // 超出内容的滚动范围
        scrollPastEnd: 0.1,
      },
      // 是否打开修改/编辑服务dialog
      openModifyServiceDialog: false,
      // 修改服务表单
      modifyServiceForm: {
        // 服务id
        id: -1,
        // 服务名称
        serviceName: "",
        // 分组名称
        groupName: "",
        // 保护阈值
        protectionThreshold: "",
        // 元数据
        metadata: "",
      },
      // 修改服务表单规则
      modifyServiceRules: {
        // 服务名称
        serviceName: [
          { required: true, message: "请输入服务名称", trigger: "blur" },
        ],
        // 保护阈值（纯整数（123）和小数（567.6512））
        protectionThreshold: [
          {
            required: true,
            pattern: /^(\-|\+)?\d+(\.\d+)?$/,
            message: "请输入保护阈值",
            trigger: "blur",
          },
        ],
      },
      // 可写的（可以进行编辑的）代码编辑器配置
      writableEditorOptions: {
        // 启用基本自动完
        enableBasicAutocompletion: true,
        //启用实时自动完成
        enableLiveAutocompletion: true,
        // 是否只读
        readOnly: false,
        // 启用代码段
        enableSnippets: true,
        // 高亮配置
        highlightActiveLine: true,
        //标签大小
        tabSize: 2,
        //设置字号
        fontSize: 18,
        //去除编辑器里的竖线
        showPrintMargin: false,
        // 超出内容的滚动范围
        scrollPastEnd: 0.1,
      },
      // 元数据过滤输入框内容
      metadataFilterInput: {
        // 元数据过滤输入框的key
        key: "",
        // 元数据过滤输入框的value
        value: "",
      },
      // 元数据过滤条件标签数组
      metadataFilterTags: [
        {
          key: "preserved.register.source",
          value: "SPRING_CLOUD",
        },
        {
          key: "aaa",
          value: "value1",
        },
        {
          key: "bbb",
          value: "value2",
        },
      ],
      // 表格数据
      tableData: [
        {
          id: 10001,
          ipAddr: "192.168.184.101",
          port: 8081,
          ephemeral: true,
          weight: 1.0,
          healthy: true,
          // 是否在线
          online: true,
          metadata:
            '{"preserved.register.source": "SPRING_CLOUD","gray": false}',
        },
        {
          id: 10002,
          ipAddr: "192.168.184.101",
          port: 8082,
          ephemeral: true,
          weight: 1.0,
          healthy: true,
          // 是否在线
          online: false,
          metadata:
            '{"preserved.register.source": "SPRING_CLOUD","gray": false}',
        },
      ],
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
    };
  },
  mounted() {
    let serviceId = this.$route.query.id;
    // 根据服务id调用后端接口从数据库查询该服务的数据
    let serviceData = {
      // 服务id
      id: serviceId,
      // 服务名称
      serviceName: "abc",
      // 分组名称
      groupName: "DEFAULT_GROUP",
      // 保护阈值
      protectionThreshold: "1",
      // 元数据
      metadata: '{"preserved.register.source": "SPRING_CLOUD","gray": false}'
    };
    // 格式化metadata的json字符串
    let metadataJSON = this.formatJsonStr(serviceData.metadata);
    // 将serviceData对象赋值给serviceForm对象(metadata要进行格式化再赋值)
    this.serviceForm = {
      // 服务id
      id: serviceData.id,
      // 服务名称
      serviceName: serviceData.serviceName,
      // 分组名称
      groupName: serviceData.groupName,
      // 保护阈值
      protectionThreshold: serviceData.protectionThreshold,
      // 元数据
      metadata: metadataJSON
    };

  },
  methods: {
    // vue2-ace-editor代码编辑器初始化(下面的额外配置（例如主题、语言等）可以在node_modules\brace文件夹找 ,然后导入即可)
    editorInit() {
      // 语言工具
      require("brace/ext/language_tools");

      // 主题（可选chrome（白）、dawn（白）、tomorrow_night（黑）、dracula（黑）、monokai（黑）等）
      require("brace/theme/tomorrow_night");

      // 编译器语言（可选html、java、javascript、golang、json、mysql、python、properties、sql、xml、yaml 等）
      require("brace/mode/json");

      // 编译器代码段（html、java、javascript、golang、json、mysql、python、properties、sql、xml、yaml 等）
      require("brace/snippets/json");
    },
    // 点击打开修改/编辑服务的dialog
    clickOpenModifyServiceDialog() {
      // 打开修改/编辑服务的dialog
      this.openModifyServiceDialog = true;
      // 将服务详情表单（serviceForm）内容复制到一个新的修改/编辑服务的表单（modifyServiceForm）对象中
      this.modifyServiceForm = {
        // 服务id
        id: this.serviceForm.id,
        // 服务名称
        serviceName: this.serviceForm.serviceName,
        // 分组名称
        groupName: this.serviceForm.groupName,
        // 保护阈值
        protectionThreshold: this.serviceForm.protectionThreshold,
        // 元数据
        metadata: this.serviceForm.metadata,
      };
    },
    // 当关闭修改服务dialog的回调
    handleCloseModifyServiceDialog() {
      this.openModifyServiceDialog = false;
    },
    // 提交修改服务表单
    modifyService() {

    },
    // JSON字符串转成Map对象
    jsonStrToMap(jsonStr) {
      const jsonObj = JSON.parse(jsonStr);
      const map = new Map();
      for (const k of Object.keys(jsonObj)) {
        map.set(k, jsonObj[k]);
      }
      return map;
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
    // 将boolean类型的ephemeral格式化成String类型
    formatEphemeral(row, column) {
      if (row.ephemeral == true) {
        return "true";
      } else {
        return "false";
      }
    },
    // 将boolean类型的healthy格式化成String类型
    formatHealthy(row, column) {
      if (row.healthy == true) {
        return "true";
      } else {
        return "false";
      }
    },
    // 将metadata的JSON字符串转成 Map然后再转成 Html字符串
    formatMetadata(row) {
      // 将JSON字符串转成Map对象
      let metadataMap = this.jsonStrToMap(row.metadata);
      // 将Map对象转成String字符串
      var metadataStr = "";
      metadataMap.forEach((value, key) => {
        metadataStr += "<p>" + key + "=" + value + "</p>";
      });

      return metadataStr;
    },
    // 添加元数据过滤条件
    addMetadataFilterCondition() {
      // 将元数据过滤输入框的内容放到元数据过滤条件标签数组中
      this.metadataFilterTags.push(this.metadataFilterInput);
      // 清空元数据过滤输入框的内容
      this.metadataFilterInput = {
        // 元数据过滤输入框的key
        key: "",
        // 元数据过滤输入框的value
        value: "",
      };
    },
    // 清空所有元数据过滤条件
    clearMetadataFilterCondition() {
      // 如果元数据过滤条件标签数组不为空,则可以清空
      if (this.metadataFilterTags.length > 0) {
        // 将元数据过滤条件标签数组中的所有元素清空
        this.metadataFilterTags = [];
      }
    },
    // 根据元数据的key删除元数据过滤条件标签
    deleteMetadataFilterTagByKey(deleteMetedataKey) {
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
    },
    // 点击打开修改/编辑实例的dialog
    clickOpenModifyInstanceDialog(row) {
      // 打开修改/编辑实例的dialog
      this.openModifyInstanceDialog = true;
      // 格式化metadata的JSON字符串
      let metadataJSON = this.formatJsonStr(row.metadata);
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
    modifyInstance() {},
    // 下线
    offline(row) {
      row.online = false;
    },
    // 上线
    online(row) {
      row.online = true;
    },
    // 返回上一个页面
    back(){
      this.$router.go(-1);
    }
  },
};
</script>

<style scoped>
.next-card-title {
  display: inline-block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  max-width: 80%;
  height: 100%;
  color: #333;
  font-size: 16px;
  font-weight: 400;
  margin-bottom: 20px;
}

.next-card-title:before {
  content: "";
  display: inline-block;
  height: 16px;
  width: 3px;
  background: #209bfa;
  position: absolute;
  left: 0;
  top: calc(26% - 8px);
}

.next-card-subtitle {
  font-size: 12px;
  color: #666;
  padding-left: 8px;
}
</style>