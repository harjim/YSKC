using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    /// <summary>
    /// 部门表
    /// </summary>
    public class ys_dept
    {
        /// <summary>
        /// 部门Id
        /// </summary>
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 部门名称
        /// </summary>
        public string deptName { get; set; }
        /// <summary>
        /// 父id
        /// </summary>
        public int parentId { get; set; }

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
        /// 创建id
        /// </summary>
        public int creatorId { get; set; }
        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime createTime { get; set; }

        /// <summary>
        /// 全路径
        /// </summary>
        [MaxLength(300)]
        public string fullPath { get; set; }

        /// <summary>
        /// 全路径
        /// </summary>
        [MaxLength(300)]
        public string fullname { get; set; }
        
        public int branchId { get; set; }

    }
}
