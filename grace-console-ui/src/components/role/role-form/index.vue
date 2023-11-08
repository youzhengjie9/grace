<template>
    <el-form ref="form" label-width="100px" :model="form">
        <el-form-item v-for="item in formLabel" :key="item.label" :label="item.label">
            <!-- 如果指定的类型是input -->
            <el-input
                v-if="item.type === 'input'"
                :placeholder="'请输入' + item.label"
                v-model="form[item.model]"
            ></el-input>
            <!-- 如果指定的类型是password -->
            <el-input
                v-if="item.type === 'password' && operateType==='add'"
                :placeholder="'请输入' + item.label"
                v-model="form[item.model]"
                show-password
            ></el-input>
            <el-input
                v-if="item.type === 'password' && operateType==='edit'"
                :placeholder="'请输入' + item.label+'（如果不输入密码则认为不修改密码）'"
                v-model="form[item.model]"
                show-password
            ></el-input>

            <el-switch v-if="item.type === 'switch'" v-model="form[item.model]">
            </el-switch>

            <!-- 如果指定的类型是date -->
            <el-date-picker
                v-if="item.type === 'date'"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择日期"
                v-model="form[item.model]"
            ></el-date-picker>
            <!-- 如果指定的类型是datetime -->
            <el-date-picker
                v-if="item.type === 'datetime'"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择日期时间"
                v-model="form[item.model]"
            ></el-date-picker>
            <!-- 如果指定的类型是select -->
            <el-select
                v-if="item.type === 'select'"
                placeholder="请选择"
                v-model="form[item.model]">
                <!-- 选择框（select下面的opts数组） -->
                <el-option
                    v-for="item in item.opts"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                ></el-option>
            </el-select>

        </el-form-item>
        <el-form-item><slot></slot></el-form-item>
    </el-form>
</template>
<script>

export default {
    name: 'RoleForm',
    props: {
        formLabel: Array,
        form: Object,
        inline: Boolean,
        operateType: String,
    },
    data () {
        return {
            T:true,
            F:false,
            curSex: '男',
        }
    }
}
</script>