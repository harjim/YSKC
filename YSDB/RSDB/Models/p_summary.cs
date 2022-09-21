using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 项目汇总表
    /// </summary>
    public class p_summary : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目id
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 月份
        /// </summary>
        public DateTime month { get; set; }

        /// <summary>
        /// 100：工资
        /// 101：养老
        /// 102：医疗
        /// 103：工伤
        /// 104：失业
        /// 105：生育
        /// 106：公积金
        /// 200：设备
        /// 201：仪器
        /// 300：材料
        /// 400：动力
        /// 401：燃料
        /// 500：检测
        /// 501：维修
        /// 502: 摊消 
        /// 503：试制
        /// 504：其他
        /// </summary>
        public int rdType { get; set; }
        /// <summary>
        /// 科目编码，后续用，先默认给空字符
        /// </summary>
        [Required, MaxLength(50)]
        public string accountNumber { get; set; }
        /// <summary>
        /// rd费用
        /// </summary>
        public decimal rdFunds { get; set; }



        public int updatorId { get; set; }

    }
}
