using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 项目成员表
    /// </summary>
    public class i_member : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目Id
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 员工编号/标识
        /// </summary>
        [Required, MaxLength(30)]
        public string enumber { get; set; }
        public int companyId { get; set; }
        /// <summary>
        /// 项目角色
        /// </summary>
        [MaxLength(100)]
        public string role { get; set; }
        /// <summary>
        /// 进入时间
        /// </summary>
        public DateTime? entryDate { get; set; }
        /// <summary>
        /// 是否是负责人
        /// </summary>
        public bool isMaster { get; set; }
         /// <summary>
        /// 年份
        /// </summary>
        public short year { get; set; }
    }
}
