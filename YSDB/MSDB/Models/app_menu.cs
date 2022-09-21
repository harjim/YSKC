using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    /// <summary>
    /// 功能菜单表
    /// </summary>
    public class app_menu
    {
        [Key]
        public int id { get; set; }

        public int parentId { get; set; }

        /// <summary>
        /// 功能菜单名
        /// </summary>
        [Required, MaxLength(30)]
        public string name { get; set; }

        /// <summary>
        /// 类型，0:菜单 1：目录 2：按钮
        /// </summary>
        public byte type { get; set; }

        /// <summary>
        /// 级别
        /// </summary>
        public byte level { get; set; }

        /// <summary>
        /// 菜单地址
        /// </summary>
        [MaxLength(200)]
        public string url { get; set; }

        /// <summary>
        /// 图标
        /// </summary>
        [Required, MaxLength(30)]
        public string icon { get; set; }

        /// <summary>
        /// 权限标识，形如：user:list,user:create
        /// </summary>
        [MaxLength(200)]
        public string perms { get; set; }

        /// <summary>
        /// 功能状态，0：不可见，1：可见
        /// </summary>
        public byte status { get; set; }

        /// <summary>
        /// 排序
        /// </summary>
        public byte seq { get; set; }

        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(300)]
        public string remark { get; set; }

        /// <summary>
        /// 全路径
        /// </summary>
        [MaxLength(300)]
        public string fullPath { get; set; }

        ///</summary>
        ///平台：0：PC，1：移动
        ///</summary>
        public int platform { get; set; }
    }
}
