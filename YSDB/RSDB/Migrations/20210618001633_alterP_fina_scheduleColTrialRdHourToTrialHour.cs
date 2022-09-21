using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterP_fina_scheduleColTrialRdHourToTrialHour : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "trialRdHour",
                table: "p_fina_schedule",
                newName: "trialHour");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "trialHour",
                table: "p_fina_schedule",
                newName: "trialRdHour");
        }
    }
}
