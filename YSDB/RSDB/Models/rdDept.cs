using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 研发组织架构
    /// </summary>
    public class rdDept : tablebase
    {
        // <summary>
        /// 部门Id
        /// </summary>
        [Key]
        public short id { get; set; }

        public int companyId { get; set; }
        /// <summary>
        /// 部门名称
        /// </summary>
        [Required, MaxLength(100)]
        public string deptName { get; set; }
        /// <summary>
        /// 父id
        /// </summary>
        public short parentId { get; set; }

        /// <summary>
        /// 部门级别
        /// </summary>
        public int level { get; set; }

        /// <summary>
        /// 部门标识, 通过id自动生成
        /// </summary>
        [Required, MaxLength(80)]
        public string identity { get; set; }

        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(300)]
        public string remark { get; set; }
        /// <summary>
        /// 年份
        /// </summary>
        public short year { get; set; }

        [MaxLength(300)]
        public string fullName { get; set; }
        /// <summary>
        /// 文字方向 0 竖向 1 横向
        /// </summary>
        public int textDirection { get; set; }
        /// <summary>
        /// 节点类型 0 普通 1委员会节点
        /// </summary>
        public int nodeType { get; set; }
        /// <summary>
        /// 序号，从1开始
        /// </summary>
        public int seq { get; set; }
        /// <summary>
        /// 对齐方式，0 左 1中 2右
        /// </summary>
        public int align { get; set; }

    }
}
