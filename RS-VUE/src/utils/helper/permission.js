const PERMISSION_ENUM = {
  add: { key: 'add', label: '新增' },
  delete: { key: 'delete', label: '删除' },
  edit: { key: 'edit', label: '修改' },
  query: { key: 'query', label: '查询' },
  get: { key: 'get', label: '详情' },
  enable: { key: 'enable', label: '启用' },
  disable: { key: 'disable', label: '禁用' },
  import: { key: 'import', label: '导入' },
  export: { key: 'export', label: '导出' }
}

function plugin (Vue) {
  if (plugin.installed) {
    return
  }

  !Vue.prototype.$auth &&
    Object.defineProperties(Vue.prototype, {
      $auth: {
        get () {
          const _this = this
          // perm: 权限标识,数组或字符串. needAll: 是否所有的权限都要存在,默认表示只要有一个就可以. prefix: 权限标识前缀
          return (perm, needAll, prefix) => {
            const permMap = _this.$store.getters.userInfo.permDataMap
            if (permMap) {
              if (Array.isArray(perm)) {
                const pre = prefix ? prefix + ':' : ''
                if (needAll) {
                  return perm.every(p => {
                    return !!permMap[pre + p]
                  })
                } else {
                  return perm.some(p => {
                    return !!permMap[pre + p]
                  })
                }
              } else {
                return !!permMap[perm]
              }
            }
            return false
          }
        }
      }
    })

  !Vue.prototype.$enum &&
    Object.defineProperties(Vue.prototype, {
      $enum: {
        get () {
          // const _this = this;
          return val => {
            let result = PERMISSION_ENUM
            val &&
              val.split('.').forEach(v => {
                result = (result && result[v]) || null
              })
            return result
          }
        }
      }
    })
}

export default plugin
