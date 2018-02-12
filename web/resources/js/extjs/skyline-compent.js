/*
该文件基于Extjs4.1
提供公共的模块以及组件
Copyright (c) 2011-2012 skyline Inc
Build date: 2012-08-27 14:30:47
*/
/**
 * 公共combox model
 */
Ext.define('comboxModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'key',type: 'string'},
        {name: 'value',type: 'string'}
    ]
});
/**
 * 公共的Ext XTemplate required 模板定义
 * 和allowBlank：false 一块使用哦
 */
var required = new Ext.XTemplate(
    '<tpl if="allowBlank === false">' +
        '<span class="mm-required">' +
        '<sup><font color="red"> *</font></sup>' +
        '</span></tpl>',
    { disableFormats: true }
);
