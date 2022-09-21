using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterP_yield_configColTrialHourToRdHour : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "trialHour",
                table: "p_yield_config",
                newName: "rdHour");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "rdHour",
                table: "p_yield_config",
                newName: "trialHour");
        }
    }
}
