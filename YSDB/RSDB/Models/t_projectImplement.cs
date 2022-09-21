using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 技改项目实施
    /// </summary>
    public class t_projectImplement : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目id，t_project
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 新增营业收入
        /// </summary>
        [Required, MaxLength(50)]
        public string businessIncome { get; set; }
        /// <summary>
        /// 新增利润
        /// </summary>
        [Required, MaxLength(50)]
        public string profit { get; set; }
        /// <summary>
        /// 新增税收
        /// </summary>
        [Required, MaxLength(50)]
        public string taxRevenue { get; set; }
        /// <summary>
        /// 预计可实现年销售收入、利润、年纳税额等
        /// </summary>
        [Required, MaxLength(100)]
        public string expect { get; set; }
        /// <summary>
        /// 项目执行期内带动的资金投入
        /// </summary>
        [Required, MaxLength(150)]
        public string drivenFundsInput { get; set; }
        /// <summary>
        /// 生产线产能提升情况
        /// </summary>
        [Required, MaxLength(50)]
        public string lineOfInfo { get; set; }
        /// <summary>
        /// 人工节约情况，人均产量提升情况
        /// </summary>
        [Required, MaxLength(100)]
        public string manpowerSavings { get; set; }
        /// <summary>
        /// 良品率提升情况
        /// </summary>
        [Required, MaxLength(50)]
        public string goodOfRate { get; set; }
        /// <summary>
        /// 单位产品能耗
        /// </summary>
        [Required, MaxLength(50)]
        public string consumptionPer { get; set; }
        /// <summary>
        /// 单位产品二氧化碳排放量
        /// </summary>
        [Required, MaxLength(50)]
        public string carbonDioxide { get; set; }
        /// <summary>
        /// 单位产品用水量
        /// </summary>
        [Required, MaxLength(50)]
        public string unitWaterUse { get; set; }
        /// <summary>
        /// 设备故障率和事故伤亡发生情况
        /// </summary>
        [Required, MaxLength(50)]
        public string casualti { get; set; }
        /// <summary>
        /// 淘汰落后设备工艺情况
        /// </summary>
        [Required, MaxLength(50)]
        public string weedOut{ get; set; }
        /// <summary>
        /// 数字化研发设计工具普及率
        /// </summary>
        [Required, MaxLength(50)]
        public string digitalize { get; set; }
        /// <summary>
        /// 关键工序数控化率
        /// </summary>
        [Required, MaxLength(50)]
        public string numericalControl { get; set; }
        /// <summary>
        /// 人才队伍建设情况
        /// </summary>
        [Required, MaxLength(50)]
        public string talentTeam { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(200)]
        public string remark { get; set; }
    }
}
