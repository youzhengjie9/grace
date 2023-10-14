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
    <el-form :model="serviceDetail" label-width="150px">
      <el-form-item label="服务名称">
        <el-input
          v-model="serviceDetail.serviceName"
          autocomplete="off"
          style="width: 70%"
          :disabled="true"
        ></el-input>
      </el-form-item>
      <el-form-item label="分组名称">
        <el-input
          v-model="serviceDetail.groupName"
          autocomplete="off"
          placeholder="DEFAULT_GROUP"
          style="width: 70%"
          :disabled="true"
        ></el-input>
      </el-form-item>
      <el-form-item label="保护阈值">
        <el-input
          v-model="serviceDetail.protectThreshold"
          autocomplete="off"
          style="width: 70%"
          :disabled="true"
        ></el-input>
      </el-form-item>
      <el-form-item label="元数据">
        <!-- 代码编辑器 -->
        <editor
          v-model="serviceDetail.metadata"
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
              :readonly="true"
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
              :readonly="true"
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
              v-model="modifyServiceForm.protectThreshold"
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
    <el-table
      :data="instanceTableData"
      border
      style="width: 100%; margin-bottom: 20%"
    >
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
          <!-- 将“展示实例的表格”中的metadata属性（Map类型）格式化成 html 字符串 -->
          <div v-html="formatTableInstanceMetadata(scope.row.metadata)"></div>
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
  props: {
    serviceDetail: Object,
    openModifyServiceDialog: Boolean,
    modifyServiceForm: Object,
    modifyServiceRules:Object,
    openModifyInstanceDialog: Boolean,
    modifyInstanceForm: Object,
  },
  data() {
    return {
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
      metadataFilterTags: [],
      // 展示实例的表格数据
      instanceTableData: [],
    };
  },
  mounted() {
    this.loadData();
  },
  watch: {
    // 当组件被加载后,自动获取props中的serviceDetail属性（ 注意: 在这里this.serviceDetail和newValue的值是一样的,使用哪个都可以 ）
    // 为什么使用watch监听props中的serviceDetail对象 ? 为了解决:在mounted等这些生命周期函数中获取不到props的值的问题
    serviceDetail(newValue, oldValue) {
      // 将服务详情表单（serviceDetail）内容复制到一个新的对象中
      let allInstances = this.serviceDetail.allInstances;

      for (let i = 0; i < allInstances.length; i++) {
        let instance = allInstances[i];
        // 深拷贝实例对象
        let newInstance = this.deepCopy(instance);

        // 将深拷贝出来的新对象放到instanceTableData集合中
        this.instanceTableData.push(newInstance);
      }
    },
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
    loadData() {},
    // 点击打开修改/编辑服务的dialog
    clickOpenModifyServiceDialog() {
      this.$emit('clickOpenModifyServiceDialog')
    },
    // 当关闭修改服务dialog的回调
    handleCloseModifyServiceDialog() {
      this.$emit('handleCloseModifyServiceDialog')
    },
    // 提交修改服务表单
    modifyService() {
      this.$emit('modifyService',this.modifyServiceForm);
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
    // 将“展示实例的表格”中的metadata属性（Object类型）格式化成 html 字符串
    formatTableInstanceMetadata(metadataObject) {
      // 将Object转成Map类型
      let metadataMap = new Map(Object.entries(metadataObject));

      // 将Map对象转成 html字符串
      var metadataHtmlStr = "";
      // 如果metadataMap有数据
      if (metadataMap.size > 0) {
        // 格式化成 html字符串
        metadataMap.forEach((value, key) => {
          metadataHtmlStr += "<p>" + key + "=" + value + "</p>";
        });
      }
      return metadataHtmlStr;
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
      for(let i = 0 ; i < metadataFilterTags.length; i++){
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
      this.$emit('clickOpenModifyInstanceDialog',row);
    },
    // 当关闭修改实例dialog的回调
    handleCloseModifyInstanceDialog() {
      this.$emit('handleCloseModifyInstanceDialog')
    },
    // 提交修改实例表单
    modifyInstance() {

    },
    // 下线
    offline(row) {
      row.online = false;
    },
    // 上线
    online(row) {
      row.online = true;
    },
    // 返回上一个页面
    back() {
      this.$router.go(-1);
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