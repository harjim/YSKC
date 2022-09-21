using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterFlowInstance_formColIsntanceIdToInstanceId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "isntanceId",
                table: "flowInstance_form",
                newName: "instanceId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "instanceId",
                table: "flowInstance_form",
                newName: "isntanceId");
        }
    }
}
