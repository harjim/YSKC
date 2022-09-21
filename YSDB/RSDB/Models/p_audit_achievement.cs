using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
  
    /// <summary>
    /// 成果管理审核
    /// </summary>
  public class p_audit_achievement
  {
    [Key]
    public int id { get; set; }
    public int moduleId { get; set; }
    public int companyId { get; set; }
    /// <summary>
    /// 成果id
    /// </summary>
    public int achievementId { get; set; }
    /// <summary>
    /// 0进行中 1通过 2驳回 3激活
    /// </summary>
    public int status { get; set; }
    public int msCreatorId { get; set; }
    public int msLastUpdatorId { get; set; }
    public DateTime createTime { get; set; }
    public DateTime lastUpdateTime { get; set; }
  }
}