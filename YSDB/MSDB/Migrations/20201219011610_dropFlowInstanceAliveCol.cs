using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class dropFlowInstanceAliveCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "alive",
                table: "flowInstance");

            migrationBuilder.AlterColumn<int>(
                name: "status",
                table: "flowInstance",
                nullable: false,
                oldClrType: typeof(bool));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<bool>(
                name: "status",
                table: "flowInstance",
                nullable: false,
                oldClrType: typeof(int));

            migrationBuilder.AddColumn<bool>(
                name: "alive",
                table: "flowInstance",
                nullable: false,
                defaultValue: false);
        }
    }
}
